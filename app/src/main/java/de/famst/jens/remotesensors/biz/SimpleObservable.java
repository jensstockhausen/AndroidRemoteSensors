package de.famst.jens.remotesensors.biz;

import java.util.ArrayList;

/**
 * Created by jens on 31/08/14.
 */
public class SimpleObservable<T>
{
    private final ArrayList<OnChangeListener> listeners = new ArrayList<OnChangeListener>();

    public void addListener(OnChangeListener listener)
    {
        synchronized (listeners)
        {
            listeners.add(listener);
        }
    }

    public void removeListener(OnChangeListener listener)
    {
        synchronized (listeners)
        {
            listeners.remove(listener);
        }
    }

    protected void notifyObservers(final T model)
    {
        synchronized (listeners)
        {
            for (OnChangeListener listener : listeners)
            {
                listener.onChange(model);
            }
        }
    }
}
