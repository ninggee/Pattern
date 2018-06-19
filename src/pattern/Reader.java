package pattern;

import com.google.gson.*;
import edu.tamu.aser.mcr.trace.AbstractNode;
import edu.tamu.aser.mcr.trace.WriteNode;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static String readFromFile(String filename) {
        try {
            File f = new File(filename);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String str;
            StringBuffer content = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                content.append(str);
            }


            getNodesFromString(content.toString());
            return content.toString();


//            System.out.println(content);

//            System.out.println(obj.get("field1").getAsString());
//            System.out.println(obj.get("field2").getAsInt() + "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";



    }

    public static List<AbstractNode> getNodesFromString(String content) {

        JsonObject obj = new JsonParser().parse(content.toString()).getAsJsonObject();

        ArrayList<AbstractNode> nodes = new ArrayList<AbstractNode>();

        JsonArray fulltrace = obj.get("fulltrace").getAsJsonArray();

        for (JsonElement o :fulltrace) {
            System.out.println(o);
            JsonObject temp = o.getAsJsonObject();

            String nodeType = temp.get("type").getAsString();

//            switch (nodeType) {
//
//            }
        }

        return nodes;

    }

    private static AbstractNode getNodeFromJsonObject(JsonObject object) {

        String type = object.get("type").getAsString();
        long GID = object.get("GID").getAsLong();
        int ID = object.get("id").getAsInt();
        String label = object.get("label").getAsString();
        long tid = object.get("tid").getAsLong();

        switch (type) {
            case "WRITE":
                String value = object.get("value").getAsString();
                String addr = object.get("addr").getAsString();
                long prevSyncId = object.get("prevSyncId").getAsLong();
                long prevBranchId = object.get("prevBranchId").getAsLong();

                WriteNode writeNode = new WriteNode(GID, tid, ID, addr, value, AbstractNode.TYPE.WRITE, label);
                writeNode.setPrevBranchId(prevBranchId);
                writeNode.setPrevSyncId(prevSyncId);
                return writeNode;
                break;

            case "READ":




//                return new WriteNode()
        }
        return null;
    }
}
