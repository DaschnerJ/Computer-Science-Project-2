import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
/**
 * 
 * @author Mr. Daschner
 * Recieved help from: http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/ArrayDeque.java#ArrayDeque
 * @param <E>
 */
public class MyDeque<E> implements Deque<E>
{
	public static void main(String[] args) 
	{
		System.out.println("Look at source code to see the data structure code.");
	}
	//Array List for object
	private E[] a;
	//Head pointer
	private int head;
	//Tail pointer
	private int tail;
	//Minimal size of undefined array
	private static final int minInitialSize = 8;
	
	//Constructors 1
    @SuppressWarnings("unchecked")
	public MyDeque() 
    {
    	a = (E[]) new Object[minInitialSize];
    	head = 0;
    	tail = 1;
    }
    //2
    public MyDeque(int size) 
    {
    	addInitialElements(size);
    	head = 0;
    	tail = 1;
    }
    //3
    public MyDeque(Collection<? extends E> c)
    {
    	addInitialElements(c.size());
    	addAll(c);
    	head = 0;
    	tail = c.size();
    	
    }
    
    //Adds initial size to the array.
    @SuppressWarnings("unchecked")
	private void addInitialElements(int size)
    {
    	int startSize = minInitialSize;
    	
    	if(size >= startSize && size < 255 && size > 0)
    	{
    		startSize = size;
    		a = (E[]) new Object[startSize];
    	}
    	else if(size >= 256)
    	{
    		throw new ArrayIndexOutOfBoundsException();
    	}
    	else 
    	{
    		throw new NullPointerException();
    	}
    	
    }
    
    
    //Checks if the array is empty.
	@Override
	public boolean isEmpty() 
	{
		return (head == tail);
	}

	//Converts the structure to an array.
	@Override
	public Object[] toArray() 
	{
		return copyOver(new Object[size()]);
	}
	
	//Copies over the array.
	private Object[] copyOver(Object[] objects) 
	{
		if(head < tail)
		{
			System.arraycopy(a, head, objects, 0, size());
		}
		else
		{
			int realHead = a.length - head;
			System.arraycopy(a, head, objects, 0, realHead);
			System.arraycopy(a, 0, objects, realHead, tail);
		}
		return objects;
	}

	//Converts this array over to an object array.
	@SuppressWarnings("unchecked")
	@Override
	public Object[] toArray(Object[] o) 
	{
		int oL = o.length;
		int s = size();
		if (oL < s)
			o = (E[])Array.newInstance(o.getClass().getComponentType(), s);
		copyOver(o);
		if (oL > s)
			o[s] = null;
		return o;
	}

	//Checks if this collection contains all collection c.
	@Override
	public boolean containsAll(Collection<?> c) 
	{
		Iterator<?> it = c.iterator();
		boolean trip = true;
		while(it.hasNext())
			if(!contains(it.next()))
				trip = false;
		return trip;
	}

	//Adds all of collection c to this collection.
	@Override
	public boolean addAll(Collection<? extends E> c) 
	{
		boolean modified = false;
        Iterator<? extends E> it = c.iterator();
        while (it.hasNext()) 
        {
        	if (add(it.next()))
        		modified = true;
        }
		return modified;
	}

	//Removes all of collection c from this collection.
	@Override
	public boolean removeAll(Collection<?> c) 
	{
		boolean modified = false;
		Iterator<?> it = c.iterator();
		while(it.hasNext())
		{
			if(c.contains(it.next()))
			{
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	//Retains only the objects in collection c for this collection, otherwise it is removed.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean retainAll(Collection c)
	{
		boolean modified = false;
		Iterator<E> it = c.iterator();
		while(it.hasNext())
		{
			if(c.contains(it.next()))
			{
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	//Clears the collection.
	@SuppressWarnings("unchecked")
	@Override
	public void clear() 
	{
		a = (E[]) new Object[minInitialSize];
		head = 0;
		tail = 1;
	}

	//Adds an element to the first position.
	@Override
	public void addFirst(E e) 
	{
		if(a == null)
		{
			throw new NullPointerException();
		}
		if(size() == a.length)
			doubleSize();
		@SuppressWarnings("unchecked")
		E[] b = (E[]) new Object[a.length];
		b[0] = e;
		tail++;
		for(int i = 0; i < size(); i++)
		{
			b[i+1] = a[i];
		}
		a = b;
	}

	//Adds an element to the last position.
	@Override
	public void addLast(E e) 
	{
		if(a == null)
		{
			throw new NullPointerException();
		}
		a[tail] = e;
		tail++;
		if(tail >= size())
		{
			doubleSize();
		}
		
		
	}
	//Expands the array as needed.
	@SuppressWarnings("unchecked")
	private void doubleSize()
	{
		E[] b = (E[]) new Object[size()*2];
		for(int i = 0; i < size(); i++)
		{
			b[i] = a[i];
		}
		a = b;
	}

	//Attempts to add an element in the first position.
	@Override
	public boolean offerFirst(E e) 
	{
		addFirst(e);
		return true;
	}

	//Attempts to add an element to the end of a position.
	@Override
	public boolean offerLast(E e) 
	{
		addLast(e);
		return true;
	}

	//Removes the first element.
	@Override
	public E removeFirst() 
	{
		E element = pollFirst();
		if(element == null)
		{
			throw new NullPointerException();
		}
		return element;
	}

	//Removes the last element.
	@Override
	public E removeLast() 
	{
		E element = pollLast();
		if(element == null)
		{
			throw new NullPointerException();
		}
		return element;
	}

	//Gets and removes the first element.
	@Override
	public E pollFirst() 
	{
		int h = head;
		E result = a[h];
		if(result == null)
			return null;
		a[h] = null;
		head = (h + 1) & (a.length - 1);
		return result;
	}

	//Gets and removes the last element.
	@Override
	public E pollLast() 
	{
		int t = (tail - 1) & (a.length - 1);
		E result = a[t];
		if(result == null)
			return null;
		a[t] = null;
		tail = t;
		return result;
	}

	//Returns the first element.
	@Override
	public E getFirst() 
	{
		if(a[head] == null)
			throw new NullPointerException();
		return a[head];
	}

	//Returns the last element.
	@Override
	public E getLast() 
	{
		if(a[tail] == null)
			throw new NullPointerException();
		return a[tail];
	}

	//Retrieves first element and does not remove.
	@Override
	public E peekFirst() 
	{
		return a[head];
	}

	//Retrieves last element and does not remove.
	@Override
	public E peekLast() 
	{
		return a[tail-1];
	}

	//Removes the first occurrence of the object within the structure.
	@Override
	public boolean removeFirstOccurrence(Object o) 
	{
		if(o == null)
			return false;
		int initial = head;
		E element;
		while( (element = a[initial]) != null)
		{
			if(o.equals(element))
			{
				removeIndex(initial);
				return true;
			}
			initial++;
		}		
		return false;
	}

	//Removes the last occurrence of the object within the structure.
	@Override
	public boolean removeLastOccurrence(Object o) 
	{
		if(o == null)
			return false;
		int initial = head;
		int lastIndice = -1;
		boolean found = false;
		E element;
		while( (element = a[initial]) != null)
		{
			if(o.equals(element))
			{
				lastIndice = initial;
				found = true;
			}
			initial++;
		}		
		if(found && lastIndice != -1)
		{
			removeIndex(lastIndice);
			return true;
		}
		return false;
	}

	//Appends an element to the array.
	@Override
	public boolean add(E e) 
	{
		addLast(e);
		return true;
	}

	//Attempts to immediately add an element.
	@Override
	public boolean offer(E e) 
	{
		return offerLast(e);
	}

	//Removes the first element.
	@Override
	public E remove() 
	{
		removeFirst();
		return null;
	}

	//Retrieves and removes the first element.
	@Override
	public E poll() 
	{
		return pollFirst();
	}

	//Obtains the first element represented by the structure.
	@Override
	public E element() 
	{
		return getFirst();
	}

	//Obtains the first element.
	@Override
	public E peek() 
	{
		return peekFirst();
	}

	//Adds the first element to the beginning of the array.
	@Override
	public void push(E e) 
	{
		addFirst(e);
	}

	//Removes the first element.
	@Override
	public E pop() 
	{
		return removeFirst();
	}

	//Removes the first occurrence of the object o.
	@Override
	public boolean remove(Object o) 
	{
		removeFirstOccurrence(o);
		return false;
	}

	//Checks if object o is contained in the array.
	@Override
	public boolean contains(Object o) 
	{
		if(o == null)
		{
			return false;
		}
		int endPosition = a.length - 1;
		int i = head;
		E element;
		while((element = a[i]) != null && endPosition != i)
		{
			if(o.equals(element))
			{
				return true;
			}
			i++;
		}
		return false;
	}

	//Gets the size of the array.
	@Override
	public int size() 
	{
		return (tail - head) & (a.length - 1);
	}

	//Returns an interator to iterate over the array.
	@Override
	public Iterator<E> iterator() 
	{
		Iterator<E> iterator = new Iterator<E>() 
		{
            int index = 0;

            @Override
            public boolean hasNext() 
            {
                return index < size() && a[index] != null;
            }

            @Override
            public E next() 
            {
            	index++;
                return a[index];
            }
        };
        return iterator;
	}

	//Returns an iterator to iterate backwards through the array.
	@Override
	public Iterator<E> descendingIterator() 
	{
		Iterator<E> dIterator = new Iterator<E>() 
		{
            int index = (tail-1);

            @Override
            public boolean hasNext() 
            {
                return index > 0 && a[index] != null;
            }

            @Override
            public E next() 
            {
            	index--;
                return a[index];
            }
        };
        return dIterator;
	}
	
	//Removes an index of the array.
	@SuppressWarnings("unchecked")
	private boolean removeIndex(int i) throws NullPointerException
	{
		E[] b = (E[]) new Object[a.length];
		int h = head;
		int t = tail - 1;
		try
		{
			int counterB = 0;
			for(int counterA = 0; counterA < a.length; i++)
			{
				if(counterA != i)
				{
					b[counterB] = a[counterA];
					counterB++;
				}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		tail = t;
		head = h;
		a = b;
		return true;	
	}

}

