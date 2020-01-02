import java.io.IOException;
import java.io.File;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.util.*;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws IOException {
    	runLC();
    	//runNN();
    }
    public static void runNN() {
    	
    }
    
    public static void runLC() {
    	Scanner scanner = new Scanner(System.in); 
        System.out.println("Choose Learning Process: \n");
        System.out.println("1. Perceptron Classifier (Hard Threshold)");
        System.out.println("2. Logistic Classifier (Soft Threshold)");
        int hardOrSoft = scanner.nextInt(); //1 is Hard 2 is Soft
        
        System.out.println("enter the name of the file (in Examples) you would like to evaluate: \n");
        String fileName = scanner.next();
        
        List<Example> ex;
        LinearClassifier ln;
        
        double accuracy;
        ArrayList<Double> trainingReportPointer;
        
        try {
        	ex = readFromFile(fileName);
        }catch (IOException e) {
        	System.out.println("Could not find that file. (Do not include Examples/ in path)");
        	scanner.close();
        	return;
        }
        
        System.out.println("Enter desired number of updates: ");
        int numUpdates = scanner.nextInt();
        
        if(hardOrSoft == 1) { // Perceptron (hard threshold)
        	ln = new PerceptronClassifier(3);
            ln.train(ex, numUpdates);
            accuracy = ln.accuracy(ex);
            trainingReportPointer = ln.getTR();
            
        } else { //logistic (soft threshold)
        	ln = new LogisticClassifier(3);
            ln.train(ex, numUpdates);
            accuracy = ln.accuracy(ex);
            trainingReportPointer = ln.getTR(); //Its Java so not an actual pointer, but thinking about it like that
        }
        //System.out.println(trainingReportPointer.size());
        System.out.println("Accuracy on training data: " + accuracy*100 + "% correct");
        initializeGraphics(trainingReportPointer, fileName);
        scanner.close();
    }
    
    public static void initializeGraphics(ArrayList<Double> trainingReportPointer, String filename) {
    	JFrame frame = new JFrame(filename);
		GraphicsImplementation graphics = new GraphicsImplementation(trainingReportPointer);
		frame.add(graphics);
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exiting one canvas will cause all 6 to close
		frame.setVisible(true);
    }

    public static List<Example> readFromFile(String filename) throws IOException {
        List<Example> data = new ArrayList<Example>();
        FileReader reader = new FileReader(new File("Examples/" + filename));
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        tokenizer.eolIsSignificant(true);
        Vector<Double> values = new Vector<Double>();
        boolean isFinished = false;
        while (!isFinished) {
            int token = tokenizer.nextToken();
            switch (token) {
                case StreamTokenizer.TT_EOF:
                    isFinished = true;
                    break;
                case StreamTokenizer.TT_EOL:
                    int nvalues = values.size();
                    
                    Example example = new Example(nvalues);
                    example.inputs[0] = 1.0;
                    for (int i = 1; i < values.size(); i++) {
                        example.inputs[i] = values.get(i-1);
                    }
                    example.output = values.lastElement();
                    data.add(example);
                    values.clear();
                    break;
                case StreamTokenizer.TT_NUMBER:
                    values.add(tokenizer.nval);
                    break;
                case StreamTokenizer.TT_WORD:
                    if (!tokenizer.sval.equals(",")) {
                        throw new IOException("Invalid Token: " + tokenizer.sval);
                    }
                    break;
            }
        }
        reader.close();
        return data;
    }
}

