
import pattern.Helper;
import pattern.Reader;
import trace.AbstractNode;
import trace.IMemNode;
import trace.TYPE;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pattern {

    public static void main(String[] args) {

        String content =  Reader.readFromFile("./json/error.json");

        List<AbstractNode> nodes = Reader.getNodesFromString(content);

        for(AbstractNode node : nodes){

            System.out.println("node:" + node);
        }

        List<String> sharedVariables = Reader.getSharedVariables(content);

        for(String sharedVariable : sharedVariables){
            System.out.println("sharedVariable:" + sharedVariable);
        }

        System.out.println(" -- - - -- - - - ");

        List<IMemNode> RWNodes = nodes.stream().filter(node -> (node.getType() == TYPE.READ || node.getType() == TYPE.WRITE))
                .filter(node -> sharedVariables.contains(((IMemNode)node).getAddr())).map(node -> (IMemNode)node).collect(Collectors.toList());



        List<pattern.Pattern> errorPatterns = getPatterns(content);

        content = Reader.readFromFile("./json/result.json");

        List<pattern.Pattern> successPtterns = getPatterns(content);

        List<pattern.Pattern> differnPattern = new ArrayList<>();


        boolean hasSame = false;
        for(pattern.Pattern errorPattern: errorPatterns) {
            for(pattern.Pattern successPattern : successPtterns) {
                if(pattern.Pattern.isTheSamePatternStrict(errorPattern, successPattern)) {
                    hasSame = true;
                    break;
                }
            }

            if(!hasSame) {
                differnPattern.add(errorPattern);
            } else {
                hasSame = false;
            }
        }

        for(pattern.Pattern similar : differnPattern) {
            System.out.println(similar);
        }

        List<pattern.Pattern> similarPatterns = Helper.getALLSimilarPatternFromNodes(differnPattern.get(1), RWNodes);

//        System.out.println(differnPattern.get(1));
//        for(pattern.Pattern similar : similarPatterns) {
//            System.out.println(similar);
//        }
    }




    public static List<pattern.Pattern> getPatterns(String content) {
        List<AbstractNode> nodes = Reader.getNodesFromString(content);
        List<String> sharedVariables = Reader.getSharedVariables(content);


        List<IMemNode> RWNodes = nodes.stream().filter(node -> (node.getType() == TYPE.READ || node.getType() == TYPE.WRITE))
                .filter(node -> sharedVariables.contains(((IMemNode)node).getAddr())).map(node -> (IMemNode)node).collect(Collectors.toList());
//
//        for (IMemNode node: RWNodes) {
//            if (node.getType() == AbstractNode.TYPE.WRITE) {
//                System.out.println(node + " " +  ((WriteNode)node).getLabel());
//            } else {
//                System.out.println(node + " " + ((ReadNode)node).getLabel());
//            }
//        }

        List<pattern.Pattern> patterns = pattern.Pattern.getPatternsFromNodes(RWNodes, 0);
        List<pattern.Pattern> falconPatterns = pattern.Pattern.getPatternsFromLengthTwoPattern(patterns);

        patterns.addAll(falconPatterns);

        return patterns;
    }

}
