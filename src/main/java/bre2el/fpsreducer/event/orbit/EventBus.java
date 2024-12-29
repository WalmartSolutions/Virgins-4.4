package bre2el.fpsreducer.event.orbit;

import bre2el.fpsreducer.event.orbit.EventBus;
import bre2el.fpsreducer.event.orbit.EventHandler;
import bre2el.fpsreducer.event.orbit.ICancellable;
import bre2el.fpsreducer.event.orbit.IEventBus;
import bre2el.fpsreducer.event.orbit.IListener;
import bre2el.fpsreducer.event.orbit.LambdaListener;
import bre2el.fpsreducer.event.orbit.NoLambdaFactoryException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class EventBus
        implements IEventBus {
    public Map<Object, List<IListener>> listenerCache = new ConcurrentHashMap<Object, List<IListener>>();
    public Map<Class<?>, List<IListener>> staticListenerCache = new ConcurrentHashMap();
    public List<LambdaFactoryInfo> lambdaFactoryInfos;
    public Map<Class<?>, List<IListener>> listenerMap = new ConcurrentHashMap();

    void unsubscribe(List<IListener> list, boolean bl) {
        for (IListener iListener : list) {
            this.unsubscribe(iListener, bl);
        }
    }

    void subscribe(List<IListener> list, boolean bl) {
        for (IListener iListener : list) {
            this.subscribe(iListener, bl);
        }
    }

    @Override
    public void unsubscribe(IListener iListener) {
        this.unsubscribe(iListener, false);
    }

    @Override
    public void subscribe(IListener iListener) {
        this.subscribe(iListener, false);
    }

    @Override
    public void unsubscribe(Object object) {
        this.unsubscribe(this.getListeners(object.getClass(), object), false);
    }

    @Override
    public void subscribe(Class<?> clazz) {
        this.subscribe(this.getListeners(clazz, null), true);
    }


    List<IListener> getListeners(Class<?> klass, Object object) {
        Function var3 = o -> {
            CopyOnWriteArrayList var4 = new CopyOnWriteArrayList();
            this.getListeners(var4, klass, object);
            return var4;
        };
        if (object == null) {
            return (List<IListener>)this.staticListenerCache.computeIfAbsent(klass, var3);
        } else {
            for (Object var5 : this.listenerCache.keySet()) {
                if (var5 == object) {
                    return (List<IListener>)this.listenerCache.get(object);
                }
            }

            List var6 = (List)var3.apply(object);
            this.listenerCache.put(object, var6);
            return var6;
        }
    }

    @Override
    public void registerLambdaFactory(String string, LambdaListener.Factory factory) {
        List<LambdaFactoryInfo> list = this.lambdaFactoryInfos;
        synchronized (list) {
            this.lambdaFactoryInfos.add(new LambdaFactoryInfo(string, factory));
        }
    }

    @Override
    public <T> T post(T t) {
        List<IListener> list = this.listenerMap.get(t.getClass());
        if (list != null) {
            for (IListener iListener : list) {
                iListener.call(t);
            }
        }
        return t;
    }

    void subscribe(IListener iListener, boolean bl) {
        if (bl) {
            if (iListener.isStatic()) {
                this.insert(this.listenerMap.computeIfAbsent(iListener.getTarget(), clazz -> new CopyOnWriteArrayList()), iListener);
            }
        } else {
            this.insert(this.listenerMap.computeIfAbsent(iListener.getTarget(), clazz -> new CopyOnWriteArrayList()), iListener);
        }
    }

    LambdaListener.Factory getLambdaFactory(Class<?> clazz) {
        List<LambdaFactoryInfo> list = this.lambdaFactoryInfos;
        synchronized (list) {
            for (LambdaFactoryInfo eventBus$LambdaFactoryInfo : this.lambdaFactoryInfos) {
                if (!clazz.getName().startsWith(eventBus$LambdaFactoryInfo.packagePrefix)) continue;
                return eventBus$LambdaFactoryInfo.factory;
            }
            throw new NoLambdaFactoryException(clazz);
        }
    }

    @Override
    public void subscribe(Object object) {
        this.subscribe(this.getListeners(object.getClass(), object), false);
    }

    void insert(List<IListener> list, IListener iListener) {
        int n;
        for (n = 0; n < list.size() && iListener.getPriority() <= list.get(n).getPriority(); ++n) {
        }
        list.add(n, iListener);
    }

    boolean isValid(Method method) {
        if (!method.isAnnotationPresent(EventHandler.class)) {
            return false;
        }
        if (method.getReturnType() != Void.TYPE) {
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        return !method.getParameters()[0].getType().isPrimitive();
    }

    void getListeners(List<IListener> list, Class<?> clazz, Object object) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!this.isValid(method)) continue;
            list.add(new LambdaListener(this.getLambdaFactory(clazz), clazz, object, method));
        }
        if (clazz.getSuperclass() != null) {
            this.getListeners(list, clazz.getSuperclass(), object);
        }
    }

    @Override
    public <T extends ICancellable> T post(T t) {
        List<IListener> list = this.listenerMap.get(t.getClass());
        if (list != null) {
            t.setCancelled(false);
            for (IListener iListener : list) {
                iListener.call(t);
                if (!t.isCancelled()) continue;
            }
        }
        return t;
    }

    @Override
    public void unsubscribe(Class<?> clazz) {
        this.unsubscribe(this.getListeners(clazz, null), true);
    }

    void unsubscribe(IListener iListener, boolean bl) {
        List<IListener> list = this.listenerMap.get(iListener.getTarget());
        if (list != null) {
            if (bl) {
                if (iListener.isStatic()) {
                    list.remove(iListener);
                }
            } else {
                list.remove(iListener);
            }
        }
    }

    public EventBus() {
        this.lambdaFactoryInfos = new ArrayList<LambdaFactoryInfo>();
    }

    public class LambdaFactoryInfo {
        public String packagePrefix;
        public LambdaListener.Factory factory;

        public LambdaFactoryInfo(String packagePrefix, LambdaListener.Factory factory) {
            this.packagePrefix = packagePrefix;
            this.factory = factory;
        }
    }
}
