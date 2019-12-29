import java.util.ArrayList;

class Pair<J, K> {
    J x;
    K y;
    
    public Pair(J x, K y) {
	this.x = x;
	this.y = y;
    }

    public String toString() {
	return "(" + x + "," + y + ")";
    }
}

public class Main {
    public static void main(String[] args) {
	System.out.println(findMinDistCrossingPoint(Problem.line1, Problem.line2));
    }

    public static Pair<Integer, Integer> operate(int x, int y, char c) {
	switch (c) {
        case 'R':
            return new Pair<Integer, Integer>(x + 1, y);
        case 'L':
            return new Pair<Integer, Integer>(x - 1, y);
        case 'U':
            return new Pair<Integer, Integer>(x, y + 1);
        case 'D':
            return new Pair<Integer, Integer>(x, y - 1);
        default:
            return null;
	}
    }
    
    public static int findMinDistCrossingPoint(String line1, String line2) {
	var positions = new ArrayList<Pair<Integer, Integer>>();
	var x = 0;
	var y = 0;
	String[] instructions = line1.split(",");
	for (String ins : instructions) {
	    var c = ins.charAt(0);
	    var num = Integer.parseInt(ins.substring(1));
	    for (var i = 0; i < num; i++) {
		Pair p = operate(x, y, c);
		x = (int)p.x;
		y = (int)p.y;
		positions.add(p);
	    }
	}
	//System.out.println(positions);
	x = 0;
	y = 0;
	var minDist = Integer.MAX_VALUE;
	var counter = 0;
	instructions = line2.split(",");
	for (String ins : instructions) {
	    var c = ins.charAt(0);
	    var num = Integer.parseInt(ins.substring(1));
	    for (var i = 0; i < num; i++) {
		counter++;
		Pair p = operate(x, y, c);
		x = (int)p.x;
		y = (int)p.y;
		for (var j = 0; j < positions.size(); j++) {
		    if ((int)positions.get(j).x == x && (int)positions.get(j).y == y) {
			//System.out.println(j + 1 + counter + " steps");
			var dist = j + 1 + counter; //Math.abs(x) + Math.abs(y);
			if (dist < minDist) {
			    minDist = dist;
			}
		    }
		}
	    }
	}
	return minDist;
    }
}
