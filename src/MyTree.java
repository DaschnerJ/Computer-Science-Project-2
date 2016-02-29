import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyTree 
{
	
	LinkedList<Node> tree = new LinkedList<Node>();

	public static void main(String[] args) 
	{

	}
	
	public Node addRoot(int r)
	{
		addRootNR(r);
		return tree.getFirst();
	}
	
	
	/**
	 * Adds a child node with the specified key c to the parent Node node.
	 * @param node Parent node to add the child to.
	 * @param c The value for the new child node.
	 * @return Returns the child once it is placed.
	 */
	public Node addChild(Node node, int c)
	{
		Node child = new Node(c);
		child.setHead(node);
		insertIntoList(child);
		return child;
	}
	
	/**
	 * Determines if a tree is full or not.
	 * @return Returns true if a tree is full and complete Returns false if it is not.
	 */
	public boolean isFull()
	{
		boolean complete = isComplete();
		boolean trip = true;
		if(complete)
		{
			Node initial = tree.getFirst();
			Node next = initial;
			while(hasNextRow(next) && trip)
			{
				if(next.getChildren() != null)
				{
					if(!(next.getChildren().size() == 2))
					{
						return false;
					}
				}
				else
				{
					return false;
				}
				while(next.hasTail())
				{
					next = next.getTail();
					if(next.getChildren() != null)
					{
						if(!(next.getChildren().size() == 2))
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
				next = getNextRowHead(next);
			}
			while(next.hasTail())
			{
				next = next.getTail();
				if(!(next.getChildren() == null))
				{
					return false;
				}
			}
		}
		return (trip && complete);
	}
	
	/**
	 * Returns if the tree is a complete tree or not.
	 * @return Returns true if the tree is complete or returns false if the tree is not.
	 */
	public boolean isComplete()
	{
		boolean flippedEndofRow = false;
		boolean complete = true;
		Node initial = tree.getFirst();
		if(initial.hasChildren())
		{
			while(hasNextRow(initial))
			{
				Node nextRow = getNextRowHead(initial);
				initial = nextRow;
				boolean initialHas = false;
				if(nextRow.hasChildren())
				{
					initialHas = true;
				}
				while(nextRow.hasTail())
				{
					nextRow = nextRow.getTail();
					if(nextRow.hasChildren())
					{
						if(initialHas)
						{
							if(flippedEndofRow)
							{
								return false;
							}
						}
						else
						{
							return false;
						}
					}
					else
					{
						if(!flippedEndofRow)
						{
							flippedEndofRow = true;
						}
					}
					
				}
				flippedEndofRow = false;
			}
		}
		return complete;	
	}
	
	/**
	 * Prints out the keys of the nodes in Pre-Order Traversal form.
	 */
	public void preOrderTraversal(Node r)
	{
		Node initial = r;
		ArrayList<Node> childrenNoted = new ArrayList<Node>();
		boolean trip = false;
		while((childrenNoted.size() != tree.size()) && !trip)
		{
			if(!childrenNoted.contains(initial))
			{
				System.out.println(initial.getKey());
			}
			else
			{
				if(initial.hasChildren())
				{
					ArrayList<Node> children = initial.getChildren();
					boolean modified = false;
					for(int i = 0; i < children.size() && !modified; i++)
					{
						if(!childrenNoted.contains(children.get(i)))
						{
							initial = children.get(i);
							modified = true;
						}
					}
					if(!modified)
					{
						if(initial.hasHead())
						{
							initial = initial.getHead();
						}
						else
						{
							trip = true;
						}	
					}
				}
				else
				{
					if(initial.hasHead())
					{
						initial = initial.getHead();
					}
					else
					{
						trip = true;
					}
				}
			}
		}
		
	}
	/**
	 * Does the breath order traversal of the structure.
	 * @param r is the root node.
	 */
	public void breathOrderTraversal(Node r)
	{
		Node initial = r;
		while(initial.hasChildren())
		{
			initial.getChildren().get(0);
		}
		ArrayList<Node> childrenNoted = new ArrayList<Node>();
		boolean trip = false;
		while((childrenNoted.size() != tree.size()) && !trip)
		{
			if(!childrenNoted.contains(initial))
			{
				System.out.println(initial.getKey());
			}
			else
			{
				if(initial.hasChildren())
				{
					ArrayList<Node> children = initial.getChildren();
					boolean modified = false;
					for(int i = 0; i < children.size() && !modified; i++)
					{
						if(!childrenNoted.contains(children.get(i)))
						{
							initial = children.get(i);
							modified = true;
						}
					}
					if(!modified)
					{
						if(initial.hasHead())
						{
							initial = initial.getHead();
						}
						else
						{
							trip = true;
						}	
					}
				}
				else
				{
					if(initial.hasHead())
					{
						initial = initial.getHead();
					}
					else
					{
						trip = true;
					}
				}
			}
		}
	}
	
	/**
	 * Prints out the node keys is post traversal form.
	 * @param r the root node to start printing out the traversal nodes.
	 */
	public void postOrderTraversal(Node r)
	{
		Node initial = r;
		ArrayList<Node> childrenNoted = new ArrayList<Node>();
		boolean trip = false;
		while(!trip && childrenNoted.size() != tree.size())
		{
			while(hasUncontained(r, childrenNoted))
			{
				initial = getFirstUncontainedChild(r, childrenNoted);
			}
			System.out.println(initial.getKey());
			childrenNoted.add(initial);
			if(initial.hasHead())
			{
				initial = initial.getHead();
			}
			else
			{
				trip = true;
			}
		}
	}
	/**
	 * Returns the first child that is not contained in the black list of children.
	 * @param r The parent node to get child from.
	 * @param list is the black list to check nodes from.
	 * @return Returns the child node that is not contained or null if none were found.
	 */
	public Node getFirstUncontainedChild(Node r, ArrayList<Node> list)
	{
		for(Node n : list)
		{
			if(!list.contains(n))
			{
				return n;
			}
		}
		return null;	
	}
	/**
	 * Checks if the there is a child of the specific node contained in the black list.
	 * @param r is he node that is being checked.
	 * @param list is the black list of children.
	 * @return Returns true if there is a node and false if there is not.
	 */
	public boolean hasUncontained(Node r, ArrayList<Node> list)
	{
		if(r.hasChildren())
		{
			for(Node n : list)
			{
				if(!list.contains(n))
				{
					return true;
				}
			}
			return false;
		}
		else
		{
			return false;
		}
	}
	
//===========================================================================================	
	
	/**
	 * Adds initial root if possible.
	 * @param r is the key to the initial root.
	 */
	public void addRootNR(int r)
	{
		if(tree.isEmpty())
		tree.add(new Node(r));
	}
	
	/**
	 * Places the child node into the list.
	 * @param n Node you wish to place, must have at least 1 parent.
	 */
	public void insertIntoList(Node n)
	{
		Node parent = n.getHead();
		//Has children
		if(parent.hasChildren())
		{
			//Get the children and get last child to insert to that index.
			ArrayList<Node> parentChildren = parent.getChildren();
			Node lastChild = parentChildren.get(parent.getChildren().size()-1);
			int insertIndex = getNodeIndex(lastChild);
			//Check if child has tail.
			Node tail = lastChild.getTail();
			//If child has a tail
			if(tail != null)
			{
				//
				n.setTail(tail);
				tail.setHead(n);
			}
			lastChild.setTail(n);
			n.setPrevious(n);
			//Copy into the list
			LinkedList<Node> b = new LinkedList<Node>();
			for(int i = 0; i < insertIndex + 1; i++)
			{
				b.add(tree.get(i));
			}
			b.add(n);
			for(int i = insertIndex; i < tree.size(); i++)
			{
				b.add(tree.get(i));
			}
			//Copy over
			tree = b;	
		}
		//No Children
		else
		{
			//Has next row
			if(hasNextRow(parent))
			{
				//Check for previous
				if(parent.hasPrevious())
				{
					//Has previous but does previous have children?
					Node previousHead = parent.getHead();
					Node parentTail = parent.getTail();
					ArrayList<Node> children = new ArrayList<Node>();
					boolean trip = true;
					boolean placed = false;
					while(children.isEmpty() && trip)
					{
						//Check if previous has children.
						if(previousHead.hasChildren())
						{
							children = previousHead.getChildren();
							//Time to insert at last Child.
							Node lastChild = children.get(previousHead.getChildren().size()-1);
							int insertIndex = getNodeIndex(lastChild);
							//Check if child has tail.
							Node tail = lastChild.getTail();
							//If child has a tail
							if(tail != null)
							{
								n.setTail(tail);
								tail.setHead(n);
							}
							lastChild.setTail(n);
							n.setPrevious(n);
							//Copy into the list
							LinkedList<Node> b = new LinkedList<Node>();
							for(int i = 0; i < insertIndex + 1; i++)
							{
								b.add(tree.get(i));
							}
							b.add(n);
							for(int i = insertIndex; i < tree.size(); i++)
							{
								b.add(tree.get(i));
							}
							//Copy over
							tree = b;
							placed = true;
						}
						//No previous or children, time to  check tails.
						else
						{
							trip = false;
						}
					}
					while(children.isEmpty() && placed)
					{
						//Check if previous has children.
						if(parentTail.hasChildren())
						{
							children = parentTail.getChildren();
							//Time to insert at last Child.
							Node lastChild = children.get(parentTail.getChildren().size()-1);
							int insertIndex = getNodeIndex(lastChild);
							//Check if child has tail.
							Node tail = lastChild.getTail();
							//If child has a tail
							if(tail != null)
							{
								n.setTail(tail);
								tail.setHead(n);
							}
							lastChild.setTail(n);
							n.setPrevious(n);
							//Copy into the list
							LinkedList<Node> b = new LinkedList<Node>();
							for(int i = 0; i < insertIndex + 1; i++)
							{
								b.add(tree.get(i));
							}
							b.add(n);
							for(int i = insertIndex; i < tree.size(); i++)
							{
								b.add(tree.get(i));
							}
							//Copy over
							tree = b;
							placed = true;
						}
					}	
				}
				//No previous must be row head.
				else if(parent.hasTail())
				{
					ArrayList<Node> children = new ArrayList<Node>();
					Node parentTail = parent.getTail();
					while(children.isEmpty())
					{
						//Check if previous has children.
						if(parentTail.hasChildren())
						{
							children = parentTail.getChildren();
							//Time to insert at last Child.
							Node lastChild = children.get(parentTail.getChildren().size()-1);
							int insertIndex = getNodeIndex(lastChild);
							//Check if child has tail.
							Node tail = lastChild.getTail();
							//If child has a tail
							if(tail != null)
							{
								n.setTail(tail);
								tail.setHead(n);
							}
							lastChild.setTail(n);
							n.setPrevious(n);
							//Copy into the list
							LinkedList<Node> b = new LinkedList<Node>();
							for(int i = 0; i < insertIndex + 1; i++)
							{
								b.add(tree.get(i));
							}
							b.add(n);
							for(int i = insertIndex; i < tree.size(); i++)
							{
								b.add(tree.get(i));
							}
							//Copy over
							tree = b;
						}
					}
				}
				else
				{
					tree.addLast(n);
				}
			}
			//There isn't another row, just append
			else
			{
				tree.addLast(n);
			}
		}
		
	}
	
	/**
	 * Gets the first left most node in a row.
	 * @param n is any node within the desired row.
	 * @return Returns the first left most node in specified row.
	 */
	public Node getFirstInRow(Node n)
	{
		Node first = n;
		while(first.hasPrevious())
		{
			first = first.getPrevious();
		}
		return n;
	}
	/**
	 * Gets the last right most node in a row.
	 * @param n is any node within the desired row.
	 * @return Returns the last right most node in the specified row.
	 */
	public Node getLastInRow(Node n)
	{
		Node last = n;
		while(last.hasTail())
		{
			last = last.getTail();
		}
		return last;
	}
	/**
	 * Gets the initial node out of the list.
	 * @return Returns the root node.
	 */
	public Node getRoot()
	{
		return tree.getFirst();
	}
	/**
	 * Checks if there exists another row.
	 * @param n is node of the row above the row you want to find.
	 * @return Returns true if there exists a row below current row. Returns false otherwise.
	 */
	public boolean hasNextRow(Node n)
	{
		Node first = getFirstInRow(n);
		boolean trip = false;
		while(!trip)
		{
			if(first.hasChildren())
				return true;
			else if(first.hasTail())
				first = first.getTail();
			else
				trip = true;
		}
		return false;
	}
	/**
	 * Gets the next row head.
	 * @param n is the node of the previous row.
	 * @return Returns the node of the next row head.
	 */
	public Node getNextRowHead(Node n)
	{
		Node first = getFirstInRow(n);
		boolean trip = false;
		while(!trip)
		{
			if(first.hasChildren())
				return getFirstInRow(getAllChildren(first).get(0));
			else if(first.hasTail())
				first = first.getTail();
			else
				trip = true;
		}
		return null;
	}
	
	/**
	 * Gets the children on a parent node.
	 * @param n is the parent node for the children.
	 * @return Returns an ArrayList<Node> of all the related children.
	 */
	public ArrayList<Node> getAllChildren(Node n)
	{
		return n.getChildren();
	}
	/**
	 * Gets the current row count for current node. Root is 0.
	 * @param n is the node for the row count.
	 * @return Returns the current row count of node n.
	 */
	public int rowNum(Node n)
	{
		int count = 0;
		Node c = getFirstInRow(n);
		while(c.hasHead())
		{
			c.getHead();
			count++;
		}
		return count;
	}
	/**
	 * Gets list index of the Node.
	 * @param n is the node to request the index of.
	 * @return Returns the index of the node.
	 */
	public int getNodeIndex(Node n)
	{
		ListIterator<Node> it = tree.listIterator();
		int index = 0;
		while(it.hasNext())
		{
			if(it.next().equals(n))
				return index;
			index++;
		}
		return -1;
	}
	
}

//=======================================================================================================================================

class Node 
{
	/**
	 * Key for the Node.
	 */
	private int key;
	/**
	 * Value of the Node for next row.
	 */
	private int value;
	/**
	 * Parent node of child node.
	 */
	private Node head;
	/**
	 * Tail of node in row.
	 */
	private Node rowTail;
	/**
	 * Previous node in row.
	 */
	private Node rowPrevious;
	/**
	 * All listed children of the parent node.
	 */
	private ArrayList<Node> children = new ArrayList<Node>();
	
	/**
	 * Initialize node with just a Key.
	 * @param k Key to the node.
	 */
	Node(int k)
	{
		key = k;
	}
	
	/**
	 * Initialize node with a key and a value.
	 * @param k Key to the node.
	 * @param v The value of the key to the next node.
	 */
	Node(int k, int v)
	{
		key = k;
		value = v;
	}
	
	/**
	 * Initialize node with key and tail.
	 * @param k Key to the node.
	 * @param r The row tail node.
	 */
	Node(int k, Node r)
	{
		key = k;
		rowTail = r;
	}
	/**
	 * Initialize node with key, value, and tail.
	 * @param k Key to the node.
	 * @param v The value of the key to the next node.
	 * @param r The row tail node.
	 */
	Node(int k, int v, Node r)
	{
		key = k;
		value = v;
		rowTail = r;
	}
	
	/**
	 * Set the node's new key value.
	 * @param k New Key to the node.
	 */
	protected void setKey(int k)
	{
		key = k;
	}
	
	/**
	 * Set the node's new head node.
	 * @param h New head node for the node.
	 */
	protected void setHead(Node h)
	{
		head = h;
	}
	
	/**
	 * Set the node's new value value.
	 * @param v New value to the node.
	 */
	protected void setValue(int v)
	{
		value = v;
	}
	/**
	 * Set the node's new head node.
	 * @param r New head node for the node.
	 */
	protected void setTail(Node r)
	{
		rowTail = r;
	}
	/**
	 * Set the node's new previous node.
	 * @param p New previous node for the node.
	 */
	protected void setPrevious(Node p)
	{
		rowPrevious = p;
	}
	/**
	 * Add a child relation to the node.
	 * @param c New child node for the node.
	 */
	protected void addChild(Node c)
	{
		children.add(c);
	}
	/**
	 * Get the node's key value.
	 * @return Returns the node's key value.
	 */
	protected int getKey()
	{
		return key;
	}
	/**
	 * Get the node's head.
	 * @return Returns the node's head.
	 */
	protected Node getHead()
	{
		return head;
	}
	/**
	 * Get the node's value.
	 * @return Returns the node's value.
	 */
	protected int getValue()
	{
		return value;
	}
	/**
	 * Get the node's tail.
	 * @return Returns the node's tail.
	 */
	protected Node getTail()
	{
		return rowTail;
	}
	/**
	 * Get the node's previous.
	 * @return Returns the node's previous.
	 */
	protected Node getPrevious()
	{
		return rowPrevious;
	}
	/**
	 * get the node's children.
	 * @return Returns the array list of the children related to the node.
	 */
	protected ArrayList<Node> getChildren()
	{
		return children;
	}
	/**
	 * Checks if the node is a parent.
	 * @return Returns true if the node is a parent and returns false if the node is not a parent.
	 */
	protected boolean hasChildren()
	{
		return !children.isEmpty();
	}
	/**
	 * Checks if the node has a head node.
	 * @return Returns true if the node has a head node and returns false if it does not.
	 */
	protected boolean hasHead()
	{
		return (head != null);
	}
	/**
	 * Checks if the node has a tail node.
	 * @return Returns true if the node has a tail node and returns false if it does not.
	 */
	protected boolean hasTail()
	{
		return (rowTail != null);
	}
	/**
	 * Checks if the node has a previous node.
	 * @return Returns true if the node has a previous node and returns false if it does not.
	 */
	protected boolean hasPrevious()
	{
		return (rowPrevious != null);
	}
	/**
	 * Removes a child node from the node's relations.
	 * @param c The child node to remove.
	 */
	protected void removeChild(Node c)
	{
		children.remove(c);
	}
}



