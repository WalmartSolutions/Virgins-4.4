package bre2el.fpsreducer.event.orbit;

import bre2el.fpsreducer.event.orbit.LambdaListener.Factory;

public interface IEventBus {
    void subscribe(IListener var1);

    <T> T post(T var1);

    void subscribe(Object var1);

    <T extends ICancellable> T post(T var1);

    void unsubscribe(Class<?> var1);

    void unsubscribe(Object var1);

    void registerLambdaFactory(String var1, Factory var2);

    void subscribe(Class<?> var1);

    void unsubscribe(IListener var1);
}

