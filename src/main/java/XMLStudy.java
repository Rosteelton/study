import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class XMLStudy {

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }


    public static String format(String xml) throws Exception {
        InputSource src = new InputSource(new StringReader(xml));
        Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(src).getDocumentElement();

        System.setProperty(DOMImplementationRegistry.PROPERTY,
                "com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");

        DOMImplementationRegistry registry = DOMImplementationRegistry
                .newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry
                .getDOMImplementation("LS");
        LSSerializer writer = impl.createLSSerializer();

        writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
        return writer.writeToString(document);
    }

    public static class Member {
        public String role;
        public String name;
        public String projectName;

        public Member(String role, String name, String projectName) {
            this.role = role;
            this.name = name;
            this.projectName = projectName;
        }
    }

    public static void main(String argv[]) {
        try {
            File file = new File("c:\\2.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("member");

            ArrayList<Member> members = new ArrayList<>();

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                NamedNodeMap attributes = fstNode.getAttributes();
                Node role = attributes.getNamedItem("role");
                Node name = attributes.getNamedItem("name");

                Node project = fstNode.getParentNode();
                NamedNodeMap projectAttributes = project.getAttributes();
                Node projectName = projectAttributes.getNamedItem("name");
                members.add(new Member(role.getTextContent(), name.getTextContent(), projectName.getTextContent()));
            }

            Map<String, ArrayList<Member>> map = new HashMap<>();

            for (Member member : members) {
                String key = member.name;
                if (map.containsKey(key)) {
                    ArrayList<Member> list = map.get(key);
                    list.add(member);

                } else {
                    ArrayList<Member> list = new ArrayList<>();
                    list.add(member);
                    map.put(key, list);
                }
            }

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("members");
            Node mainNode = document.appendChild(rootElement);


            for (Map.Entry<String, ArrayList<Member>> entry : map.entrySet()) {
                Element el = document.createElement("member");
                el.setAttribute("name", entry.getKey());
                for (Member e : entry.getValue()) {
                    Element role = document.createElement("role");
                    role
                            .setAttribute("name", e.role);
                    role.setAttribute("project", e.projectName);

                    el.appendChild(role);
                }
                mainNode.appendChild(el);
            }
            System.out.println(format(toString(document)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}