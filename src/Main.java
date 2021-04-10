import java.util.*;
import java.io.*;

//노드 클래스 선언
class Node {
    public Node left; //왼쪽 노드
    public Node right;  //오른쪽 노드
    public char ch;  //하나의 문자를 의미
    public int frequency;  //문자의 빈도수를 의미한다
}

//빈도수로 우선순의 큐를 정렬하는 클래스 선언
//빈도수가 낮은 노드를 우선순위가 높게 함
class frecom implements Comparator<Node>{
    public int compare(Node x, Node y){
        int frequencyX = x.frequency;
        int frequencyY = y.frequency;
        return frequencyX - frequencyY;
    }
}

public class Main {
    //우선순위 큐 선언
    public static PriorityQueue<Node> queue;
    //hashmap 할당
    public static HashMap<Character, String> charToCode = new HashMap<Character, String>();

    //허프만코드 트리 작성
    //문자 빈도수 기준으로 작성
    public static Node HUFFMAN(int n) {

        //빈도수가 낮은 노드 2개를 선택해서 압축 진행
        //빈도수를 합친후에 우선순위 큐로 다시 add
        for (int i = 0; i < n - 1; i++) {
            Node z = new Node();
            z.left = queue.poll(); //left는 빈도수가 낮음
            z.right = queue.poll(); //right은 빈도수가 높음
            z.frequency = z.right.frequency + z.left.frequency; // 두 노드의 빈도수 합침
            queue.add(z);
        }
        return queue.poll();
    }
    public static void main(String[] args){
        String txt = new Scanner(System.in).next();  //형태를 String으로 변환
        HashMap<Character, Integer> check = new HashMap<Character, Integer>();  //빈도수를 확인하는 변수 check 선언

        for(int i=0; i< txt.length(); i++)  // 전체 문자열의 길이만큼 반복
       {
           char temp = txt.charAt(i);  //빈공간인 temp에 문자를 넣어줌

           //문자가 이미 있는 경우: 기존의 크기에 1 더해줌
           if(check.containsKey(temp))  //containsKey로 문자가 있는지 확인
               check.put(temp, check.get(temp)+1);
           //문자가 처음 들어가는 경우: 크기를 1로 해줌
           else
               check.put(temp, 1);
        }

        //Node 전체를 우선순위 큐에 저장
        queue = new PriorityQueue<Node>(100, new frecom());
        int number=0;

        //문자, 빈도수가 저장된 노드를 우선순위 큐에 저장
        for(Character C : check.keySet()){
            Node temp = new Node();
            temp.ch = C;
            temp.frequency = check.get(C);
            queue.add(temp);
            number ++;
        }

        Node root = HUFFMAN(number);  //우선순위 큐에서 노드를 재배열
        traversal(root, new String()); //변수 길이 코드

        //결과
        String result = new String();
        for (int i=0; i<txt.length(); i++)
            result = result + charToCode.get(txt.charAt(i))+""; System.out.println(result);
    }

    //오른쪽 노드에는 1, 왼쪽 노드에는 0을 부여한 후에 해시 맵과 대조 후 문자열을 0과 1로 출력
   public static void traversal(Node n, String s) {
        if(n == null)
            return;
        traversal(n.left, s + "0");
        traversal(n.right, s + "1");
        if(n.ch != '\0') {
            System.out.println(n.ch + ": " + s);
            charToCode.put(n.ch, s);
        }
    }
}