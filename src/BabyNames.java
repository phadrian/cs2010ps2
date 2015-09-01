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
	private BST bst;

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
		}
		// all these attributes remain public to slightly simplify the code
		public BSTVertex parent, left, right;
		public Name key;
		public int height; // will be used in AVL lecture
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
				if (T.left == null && T.right == null)                   // this is a leaf
					T = null;                                      // simply erase this node
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
			else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
		}

		public int getHeight() { return getHeight(root); }
		
		public BSTVertex getRoot() { return root; }
		
	}


	// --------------------------------------------

	public BabyNames() {
		// Write necessary code during construction;
		//
		// write your answer here

		// --------------------------------------------
		bst = new BST();

		// --------------------------------------------
	}

	void AddSuggestion(String babyName, int genderSuitability) {
		// You have to insert the information (babyName, genderSuitability)
		// into your chosen data structure
		//
		// write your answer here

		// --------------------------------------------
		Name name = new Name(babyName, genderSuitability);
		bst.insert(name);
		// --------------------------------------------
	}

	void RemoveSuggestion(String babyName) {
		// You have to remove the babyName from your chosen data structure
		//
		// write your answer here

		// --------------------------------------------
		// Use dummy value for genderSuitability because no comparison is made
		Name name = new Name(babyName, 0);
		bst.delete(name);

		// --------------------------------------------
	}

	int Query(String START, String END, int genderPreference) {

		// You have to answer how many baby name starts
		// with prefix that is inside query interval [START..END)
		//
		// write your answer here
		return genderMatchWithinInterval(bst.getRoot(), START, END, genderPreference);
	}
	
	int genderMatchWithinInterval(BSTVertex T, String start, String end, int genderPreference) {
		if (T == null) {
			return 0;
		} else if (isWithinRange(T.key.babyName, start, end) && isRightGender(T.key, genderPreference)) {
			return 1 + genderMatchWithinInterval(T.left, start, end, genderPreference) + 
					genderMatchWithinInterval(T.right, start, end, genderPreference);
		} else {
			return genderMatchWithinInterval(T.left, start, end, genderPreference) + 
					genderMatchWithinInterval(T.right, start, end, genderPreference);
		}
	}
	
	boolean isRightGender(Name name, int genderMatch) {
		return name.genderSuitability == genderMatch || genderMatch == 0;
	}
	
	boolean isWithinRange(String input, String start, String end) {
		return input.compareTo(start) >= 0 && input.compareTo(end) < 0;
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
