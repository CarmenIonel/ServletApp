package Utilities;

public class ParseXML {

    public String parse(String text){

        int start=0, stop=0;
        String info;

        for(int i=1; i<text.length()-1; i++) {
            if (text.charAt(i) == '>')
                start = i;
            if(text.charAt(i)=='<')
                stop = i;
        }
        info=text.substring(start+1 ,stop);
        return info;
    }
}
