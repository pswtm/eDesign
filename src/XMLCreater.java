/**
 * Created by XQF on 24.04.2017.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XMLCreater {

    public String create(String xmlstring) {
        xmlstring += "			XML Datei\n"
                + "        <leer>\n"
                + "                <name>XML File</name>\n"
                + "		<Teile>\n\n"
                + "		<Kondensator>" + "Kondensator" + "</Kondensator>\n" //Kondensatorname in fett
                + "		<x>" + "xkon" + "</x>\n"
                + "		<y>" + "ykon" + "</y>\n"
                + "		<Spule>" + "Spule" + "</Spule>\n"
                + "		<x>" + "xspu" + "</x>\n"
                + "		<y>" + "yspu" + "</y>\n"
                + "		<Widerstand>" + "Widerstand" + "</Widerstand>\n"
                + "		<x>" + "xwid" + "</x>\n"
                + "		<y>" + "ywid" + "</y>\n"
                + "		<Spannungsquelle>" + "Spannungsquelle" + "</Spannungsquelle>\n"
                + "		<x>" + "xspa" + "</x>\n"
                + "		<y>" + "yspa" + "</y>\n"
                + "\n"
                + "		</Teile>\n"
                + "        </leer>\n";
        return xmlstring;
    }
}
