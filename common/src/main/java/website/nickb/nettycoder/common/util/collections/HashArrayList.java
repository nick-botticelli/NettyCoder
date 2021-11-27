package website.nickb.nettycoder.common.util.collections;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A list that is backed by a HashMap to provide hashing functionality for O(1) performance for
 * {@link #contains(Object)}. Uses reference equality.
 *
 * @param <T> the type of elements in this list
 */
public class HashArrayList<T> implements List<T>
{
    private final ReferenceArrayList<T> list;
    private final Reference2IntOpenHashMap<T> backingHashMap;

    public HashArrayList(@NotNull List<T> list)
    {
        this.list = new ReferenceArrayList<>();
        this.list.addAll(list);

        // Use hashing functionality from HashMap
        backingHashMap = new Reference2IntOpenHashMap<>();
        backingHashMap.defaultReturnValue(0);

        for (T element : list)
            backingHashMap.addTo(element, 1);
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o)
    {
        return backingHashMap.containsKey((T) o);
    }

    @Override
    public Iterator<T> iterator()
    {
        return listIterator();
    }

    @Override
    public Object[] toArray()
    {
        return list.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T1> T1[] toArray(T1 @NotNull [] a)
    {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t)
    {
        addHash(t);
        return list.add(t);
    }

    @Override
    public boolean remove(Object o)
    {
        removeHash(o);
        return list.remove(o);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object element : c)
            if (!backingHashMap.containsKey((T) element))
                return false;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        for (T element : c)
            addHash(element);

        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        for (T element : c)
            addHash(element);

        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c)
    {
        backingHashMap.keySet().removeAll(c);
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c)
    {
        backingHashMap.keySet().retainAll(c);
        return list.retainAll(c);
    }

    @Override
    public void clear()
    {
        backingHashMap.clear();
        list.clear();
    }

    @Override
    public T get(int index)
    {
        return list.get(index);
    }

    @Override
    public T set(int index, T element)
    {
        T prev = list.set(index, element);

        if (prev != element)
        {
            if (prev != null)
                removeHash(prev);

            addHash(element);
        }

        return prev;
    }

    @Override
    public void add(int index, T element)
    {
        addHash(element);
        list.add(index, element);
    }

    @Override
    public T remove(int index)
    {
        T prev = list.remove(index);

        if (prev != null)
            removeHash(prev);

        return prev;
    }

    @Override
    public int indexOf(Object o)
    {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return list.lastIndexOf(o);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator()
    {
        return listIterator(0);
    }

    @Override
    @NotNull
    public ListIterator<T> listIterator(int index)
    {
        return new ListIterator<T>()
        {
            private final ListIterator<T> inner = list.listIterator(index);

            @Override
            public boolean hasNext()
            {
                return inner.hasNext();
            }

            @Override
            public T next()
            {
                return inner.next();
            }

            @Override
            public boolean hasPrevious()
            {
                return inner.hasPrevious();
            }

            @Override
            public T previous()
            {
                return inner.previous();
            }

            @Override
            public int nextIndex()
            {
                return inner.nextIndex();
            }

            @Override
            public int previousIndex()
            {
                return inner.previousIndex();
            }

            @Override
            public void remove()
            {
                int prevIndex = previousIndex();

                if (prevIndex == -1)
                    throw new NoSuchElementException();

                T prev = get(prevIndex);

                if (prev != null)
                    removeHash(prev);

                inner.remove();
            }

            @SuppressWarnings("unchecked")
            @Override
            public void set(Object t)
            {
                int last = previousIndex();

                if (last == -1)
                    throw new NoSuchElementException();

                T prev = get(last);

                if (prev != t)
                {
                    if (prev != null)
                        removeHash(prev);

                    addHash((T) t);
                }

                inner.remove();
            }

            @SuppressWarnings("unchecked")
            @Override
            public void add(Object t)
            {
                addHash((T) t);
                inner.add((T) t);
            }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    private void addHash(T t)
    {
        backingHashMap.addTo(t, 1);
    }

    @SuppressWarnings("unchecked")
    private void removeHash(Object o)
    {
        if (backingHashMap.addTo((T) o, -1) <= 1)
            backingHashMap.removeInt(o);
    }
}
