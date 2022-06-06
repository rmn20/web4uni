
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Roman
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		File file = new File("data.txt");
		Tree tree = null;
		
		//Загружаем дерево
		try {
			Scanner fileSn = new Scanner(file);
			
			tree = new Tree();
			
			while(fileSn.hasNext()) {
				Node node = new Node();
				node.scanData(fileSn);
				tree.add(node);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(tree == null) return;
		
		//Основной цикл программы
		Scanner sn = new Scanner(System.in);
		
		while(true) {
			System.out.println("Введите 0, чтобы добавить запись");
			System.out.println("Введите 1, чтобы удалить запись");
			System.out.println("Введите 2, чтобы найти запись");
			System.out.println("Введите 3, чтобы вывести дерево");
			System.out.println("Введите любое другое число, чтобы закрыть программу");
			
			int i = sn.nextInt();
			
			if(i == 0) {
				System.out.println("Введите номер накладной, название товара и скидку:");
				Node node = new Node();
				node.scanDataUser(sn);
				tree.add(node);
			} else if(i == 1) {
				System.out.println("Введите номер накладной:");
				i = sn.nextInt();
				tree.remove(i);
			} else if(i == 2) {
				System.out.println("Введите номер накладной:");
				i = sn.nextInt();
				Node node = tree.find(i);
				
				if(node == null) System.out.println("Не удаётся найти узел");
				else node.printData();
			} else if(i == 3) {
				tree.print();
			} else break;
		}
	}
	
}

class Tree {
	public Node root;
	
	//Бинарный поиск
	public Node find(int id) {
		Node node = root;
		
		while(node != null && node.id != id) {
			node = id > node.id ? node.right : node.left;
		}
		
		if(node == null) return null;
		return node.id == id ? node : null;
	}
	
	public void add(Node node) {
		if(root == null) {
			root = node;
			node.parent = null;
			return;
		}
		
		//Ищем подходящий нод без ребёнка
		Node prev = null;
		Node x = root;
		
		while(x != null) {
			prev = x;
			x = node.id > x.id ? x.right : x.left;
		}
		
		if(node.id > prev.id) prev.right = node;
		else prev.left = node;
		node.parent = prev;
	}
	
	public void remove(Node z) {
		if(z.left == null) {
			shiftNodes(z, z.right);
		} else if(z.right == null) {
			shiftNodes(z, z.left);
		} else {
			Node y = TreeSuccessor(z);
			
			if(y.parent != z) {
				shiftNodes(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			shiftNodes(z, y);
			y.left = z.left;
			y.left.parent = y;
		}
	}
	
	private void shiftNodes(Node u, Node v) {
		if(u.parent == null) {
			root = v;
		} else if(u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		if(v != null) {
			v.parent = u.parent;
		}
	}
	
	private Node TreeSuccessor(Node x) {
		if(x.right != null) {
			return TreeMinimum(x.right);
		}
		
		Node y = x.parent;
		while(y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		
		return y;
	}
	
	//Рекурсивная функция
	public void print() {
		print(root);
	}
	
	public void print(Node node) {
		if(node == null) return;
		System.out.println(
			node.id + 
			" " + (node.left != null ? node.left.id : "null") + 
			" " + (node.right != null ? node.right.id : "null")
		);
		
		print(node.left);
		print(node.right);
	}
}

class Node {
	
	public int id;
	public Node left, right, parent;
	
	private String name;
	private int discount;
	
	public void printData() {
		System.out.println("Номер накладной: " + id);
		System.out.println("Название товара: " + name);
		System.out.println("Кол-во: " + discount);
	}
	
	public void scanData(Scanner sn) {
		id = Integer.parseInt(sn.nextLine());
		name = sn.nextLine();
		discount = Integer.parseInt(sn.nextLine());
	}
	
	//nextLine не работает в консоли...
	public void scanDataUser(Scanner sn) {
		id = Integer.parseInt(sn.next());
		name = sn.next();
		discount = Integer.parseInt(sn.next());
	}
	
}
