//Copy paste this Java Template and save it as "BabyNames.java"
import java.util.*;
import java.io.*;
import java.util.NoSuchElementException; // we will use this to illustrate Java Error Handling mechanism

//write your matric number here:
//write your name here:
//write list of collaborators here:
//year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

public class BabyNames {
	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	private AVL avlMale;
	private AVL avlFemale;

	// --------------------------------------------
	class Name {
		public String babyName;
		public int genderSuitability;
		public Name(String inputName, int inputGenderSuitability) {
			babyName = inputName;
			genderSuitability = inputGenderSuitability;
		}
	}
	
	// Every vertex in this BST is a Java Class
	class BSTVertex {
		BSTVertex(Name v) { 
			key = v; 
			parent = left = right = null; 
			height = 0;
			size = 1;
		}
		// all these attributes remain public to slightly simplify the code
		public BSTVertex parent, left, right;
		public Name key;
		public int height; // will be used in AVL lecture
		public int size;
	}

	// This is just a sample implementation
	// There are other ways to implement BST concepts...
	class BST {
		protected BSTVertex root;

		protected BSTVertex search(BSTVertex T, Name v) {
			if (T == null)  return T;                                  // not found
			else if (T.key.babyName.equals(v.babyName)) return T;                                      // found
			else if (T.key.babyName.compareTo(v.babyName) < 0)  return search(T.right, v);       // search to the right
			else                 return search(T.left, v);         // search to the left
		}

		protected BSTVertex insert(BSTVertex T, Name v) {
			if (T == null) return new BSTVertex(v);          // insertion point is found

			if (T.key.babyName.compareTo(v.babyName) < 0) {                                      // search to the right
				T.right = insert(T.right, v);
				T.right.parent = T;
			}
			else {                                                 // search to the left
				T.left = insert(T.left, v);
				T.left.parent = T;
			}

			T.height = 1 + Math.max(getHeight(T.left), getHeight(T.right));
			return T;                                          // return the updated BST
		}

		protected void inorder(BSTVertex T) {
			if (T == null) return;
			inorder(T.left);                               // recursively go to the left
			System.out.printf(" %d", T.key);                      // visit this BST node			
			inorder(T.right);                             // recursively go to the right
		}

		// Example of Java error handling mechanism
		/* // old code, returns -1 when there is no minimum (the BST is empty)
	  protected int findMin(BSTVertex T) {
	         if (T == null)      return -1;                             // not found
	    else if (T.left == null) return T.key;                    // this is the min
	    else                     return findMin(T.left);           // go to the left
	  }
		 */

		protected Name findMin(BSTVertex T) {
			if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
			else if (T.left == null) return T.key;                    // this is the min
			else                     return findMin(T.left);           // go to the left
		}

		protected Name findMax(BSTVertex T) {
			if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
			else if (T.right == null) return T.key;                   // this is the max
			else                      return findMax(T.right);        // go to the right
		}

		protected Name successor(BSTVertex T) {
			if (T.right != null)                       // this subtree has right subtree
				return findMin(T.right);  // the successor is the minimum of right subtree
			else {
				BSTVertex par = T.parent;
				BSTVertex cur = T;
				// if par(ent) is not root and cur(rent) is its right children
				while ((par != null) && (cur == par.right)) {
					cur = par;                                         // continue moving up
					par = cur.parent;
				}
				return par == null ? null : par.key;           // this is the successor of T
			}
		}

		protected Name predecessor(BSTVertex T) {
			if (T.left != null)                         // this subtree has left subtree
				return findMax(T.left);  // the predecessor is the maximum of left subtree
			else {
				BSTVertex par = T.parent;
				BSTVertex cur = T;
				// if par(ent) is not root and cur(rent) is its left children
				while ((par != null) && (cur == par.left)) { 
					cur = par;                                         // continue moving up
					par = cur.parent;
				}
				return par == null ? null : par.key;           // this is the successor of T
			}
		}

		protected BSTVertex delete(BSTVertex T, Name v) {
			if (T == null)  return T;              // cannot find the item to be deleted

			if (T.key.babyName.equals(v.babyName)) {                          // this is the node to be deleted
				if (T.left == null && T.right == null) {                   // this is a leaf
					T = null;                                      // simply erase this node
				}
				else if (T.left == null && T.right != null) {   // only one child at right
					T.right.parent = T.parent;
					T = T.right;                                                 // bypass T
				}
				else if (T.left != null && T.right == null) {    // only one child at left
					T.left.parent = T.parent;
					T = T.left;                                                  // bypass T
				}
				else {                                 // has two children, find successor
					Name successorV = successor(v);
					T.key = successorV;         // replace this key with the successor's key
					T.right = delete(T.right, successorV);      // delete the old successorV
				}
			}
			else if (T.key.babyName.compareTo(v.babyName) < 0)                                   // search to the right
				T.right = delete(T.right, v);
			else                                                   // search to the left
				T.left = delete(T.left, v);
			
			T.height = 1 + Math.max(getHeight(T.left), getHeight(T.right));
			return T;                                          // return the updated BST
		}

		public BST() { root = null; }

		public Name search(Name v) {
			BSTVertex res = search(root, v);
			return res == null ? null : res.key;
		}

		public void insert(Name v) { root = insert(root, v); }

		public void inorder() { 
			inorder(root);
			System.out.println();
		}

		public Name findMin() { return findMin(root); }

		public Name findMax() { return findMax(root); }

		public Name successor(Name v) { 
			BSTVertex vPos = search(root, v);
			return vPos == null ? null : successor(vPos);
		}

		public Name predecessor(Name v) { 
			BSTVertex vPos = search(root, v);
			return vPos == null ? null : predecessor(vPos);
		}

		public void delete(Name v) { root = delete(root, v); }

		// will be used in AVL lecture
		protected int getHeight(BSTVertex T) {
			if (T == null) return -1;
			else return T.height;
		}

		public int getHeight() { return getHeight(root); }

		protected void updateHeight(BSTVertex T) {
			if (T != null) {
				T.height = 1 + Math.max(getHeight(T.left), getHeight(T.right));
			}
		}
		
		protected void updateSize(BSTVertex T) {
			if (T != null) {
				T.size = 1 + getSize(T.left) + getSize(T.right);
			}
		}
		
		protected int getSize(BSTVertex T) {
			if (T == null) {
				return 0;
			} else {
				return T.size;
			}
		}
		
		public BSTVertex getRoot() { return root; }
	}

	class AVL extends BST {
		
		private BSTVertex rotateLeft(BSTVertex T) {
			BSTVertex w = T.right;			
			// Connect the right node of T (which is w) to the parent of T
			w.parent = T.parent;
			// Put w above T
			T.parent = w;
			// Left node of w is shifted to right of T to replace w
			T.right = w.left;
			// Need to set the parent of w.left unless w.left does not exist
			if (w.left != null) {
				w.left.parent = T;
			}
			// Set T, which is under w, to w.left
			w.left = T;
			
			if (w.parent != null) {
				if (w.parent.left == T) {
					w.parent.left = w;
				} else {
					w.parent.right = w;
				}
			}
			updateHeight(T);
			updateHeight(w);
			// Only T and w has new sizes
			w.size = T.size;
			T.size = getSize(T.left) + getSize(T.right) + 1;
			return w;
		}
		
		private BSTVertex rotateRight(BSTVertex T) {
			BSTVertex w = T.left;
			w.parent = T.parent;
			T.parent = w;
			T.left = w.right;
			if (w.right != null) {
				w.right.parent = T;
			}
			w.right = T;
			
			if (w.parent != null) {
				if (w.parent.left == T) {
					w.parent.left = w;
				} else {
					w.parent.right = w;
				}
			}
			updateHeight(T);
			updateHeight(w);
			w.size = T.size;
			T.size = getSize(T.left) + getSize(T.right) + 1;
			return w;
		}
		
		public int getBalanceFactor(BSTVertex T) { 
			if (T == null) {
				return 0;
			} else {
				return getHeight(T.left) - getHeight(T.right);
			}
		}
		
		public BSTVertex balance(BSTVertex T) {
			// Check the balance factor
			if (getBalanceFactor(T) == 2 && getBalanceFactor(T.left) == 1) {
				T = rotateRight(T);
			} else if (getBalanceFactor(T) == 2 && getBalanceFactor(T.left) == -1) {
				T.left = rotateLeft(T.left);
				T = rotateRight(T);
			} else if (getBalanceFactor(T) == -2 && getBalanceFactor(T.right) == 1) {
				T.right = rotateRight(T.right);
				T = rotateLeft(T);
			} else if (getBalanceFactor(T) == -2 && getBalanceFactor(T.right) == -1) {
				T = rotateLeft(T);
			} else {
				; // No need to do anything
			}
			return T;
		}

		@Override
		protected BSTVertex insert(BSTVertex T, Name v) {
			if (T == null) return new BSTVertex(v);          // insertion point is found

			if (T.key.babyName.compareTo(v.babyName) < 0) {                                      // search to the right
				T.right = insert(T.right, v);
				T.right.parent = T;
			}
			else {                                                 // search to the left
				T.left = insert(T.left, v);
				T.left.parent = T;
			}
			updateHeight(T);
			T.size++;
			// Balance the tree after insertion
			T = balance(T);
			return T;                                          // return the updated BST
		}
		
		@Override
		protected BSTVertex delete(BSTVertex T, Name v) {
			if (T == null)  return T;              // cannot find the item to be deleted

			if (T.key.babyName.equals(v.babyName)) {                          // this is the node to be deleted
				if (T.left == null && T.right == null) {                  // this is a leaf
					T = null;                                      // simply erase this node
				}
				else if (T.left == null && T.right != null) {   // only one child at right
					T.right.parent = T.parent;
					T = T.right;                                                 // bypass T
				}
				else if (T.left != null && T.right == null) {    // only one child at left
					T.left.parent = T.parent;
					T = T.left;                                                  // bypass T
				}
				else {                                 // has two children, find successor
					Name successorV = successor(v);
					T.key = successorV;         // replace this key with the successor's key
					T.right = delete(T.right, successorV);      // delete the old successorV
				}
			}
			else if (T.key.babyName.compareTo(v.babyName) < 0) {                                   // search to the right
				T.right = delete(T.right, v);
			}
			else {                                                  // search to the left
				T.left = delete(T.left, v);
			}
			updateHeight(T);
			updateSize(T);
			// Balance the tree after deletion
			T = balance(T);
			return T;                                          // return the updated BST
		}

		private int lesserThan(BSTVertex T, String key) {
			if (T == null) {
				return 0;
			} else if (T.key.babyName.compareTo(key) < 0) {
				return getSize(T.left) + 1 + lesserThan(T.right, key);
			} else if (T.key.babyName.compareTo(key) > 0) {
				return lesserThan(T.left, key);
			} else if (T.key.babyName.compareTo(key) == 0) {
				return getSize(T.left);
			} else {
				// Dummy branch
				return 0;
			}
		}
		
		public int lesserThan(String key) {
			return lesserThan(root, key);
		}
	}

	// --------------------------------------------

	public BabyNames() {
		avlMale = new AVL();
		avlFemale = new AVL();
	}

	void AddSuggestion(String babyName, int genderSuitability) {

		Name name = new Name(babyName, genderSuitability);
		if (genderSuitability == 1) {
			avlMale.insert(name);
		} else { // Assume genderSuitability == 2
			avlFemale.insert(name);
		}
		// --------------------------------------------
	}

	void RemoveSuggestion(String babyName) {
		
		// Dummy value for genderSuitability
		Name name = new Name(babyName, 0);
		avlMale.delete(name);
		avlFemale.delete(name);
	}

	int Query(String START, String END, int genderPreference) {

		if (genderPreference == 1) {
			return avlMale.lesserThan(END) - avlMale.lesserThan(START);
		} else if (genderPreference == 2) {
			return avlFemale.lesserThan(END) - avlFemale.lesserThan(START);
		} else { // Last case genderPreference == 0
			return (avlMale.lesserThan(END) - avlMale.lesserThan(START)) + 
					(avlFemale.lesserThan(END) - avlFemale.lesserThan(START));
		}
	}

	void run() throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated judging
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			if (command == 0) // end of input
				break;
			else if (command == 1) // AddSuggestion
				AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
			else if (command == 2) // RemoveSuggestion
				RemoveSuggestion(st.nextToken());
			else // if (command == 3) // Query
				pr.println(Query(st.nextToken(), // START
						st.nextToken(), // END
						Integer.parseInt(st.nextToken()))); // GENDER
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated judging
		BabyNames ps2 = new BabyNames();
		ps2.run();
	}
}
