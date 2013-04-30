/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mvbostongueutil;

import br.com.mvbos.IOWorkUtil.IOFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mbecker
 */
public class TongueUtil {

    public static final String ARQUIVO_FRASES_ACENTUADAS = "frases_acent.dic";
    public static final String ESPACO = " ";
    public static final String LINHA = "\r\n";
    public static final String PONTO = ".";
    public static final int SEPARADOR_CONTADOR = 2;
    public static final String SEPARADOR_FRASES_ACENTUADAS = "=";
    public static final String UNDER_LINE = "_";

    public static String UPPER_FIRT(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return str.length() == 1 ? str.toUpperCase()
                    : String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
        }
        return str;
    }

    public static String LOWER_FIRT(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return str.length() == 1 ? str.toLowerCase()
                    : String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1);
        }
        return str;
    }

    public static String primeiraMaiscula(String str) {
        return LOWER_FIRT(str);
    }

    public static String primeiraMinuscula(String str) {
        return UPPER_FIRT(str);
    }

    public static String javaNomeVariavel(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }

        String temp = removerAcento(str.trim());
        temp = LOWER_FIRT(temp);
        temp = temp.replaceAll(ESPACO, UNDER_LINE);
        String[] _arr = temp.split(UNDER_LINE);
        temp = _arr[0];
        if (_arr.length > 1) {
            for (int i = 1; i < _arr.length; i++) {
                temp += UPPER_FIRT(_arr[i]);
            }
        }

        return temp;
    }

    public static String javaNomeConstante(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }

        String temp = removerAcento(str.trim());
        temp = temp.replaceAll(ESPACO, UNDER_LINE).toUpperCase();

        return temp;
    }

    public static String removerExtensao(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }
        return str.substring(0, str.lastIndexOf(PONTO));
    }
    public static Map<String, String> acentoMap;

    public static String adicionarAcento(String str) {
        //used dic from: https://addons.mozilla.org/en-US/firefox/addon/verificador-ortogr%C3%A1fico-para-p/contribute/roadblock/?src=dp-btn-primary&version=2.1.1-2.0

        if (str == null || str.trim().isEmpty()) {
            return str;
        }

        if (acentoMap == null) {
            File f = new File(ARQUIVO_FRASES_ACENTUADAS);
            IOFile io = new IOFile();
            String content = io.load(f);

            if (content != null && !content.trim().isEmpty()) {
                String arr[] = content.split(LINHA);
                acentoMap = new HashMap<String, String>(arr.length);

                for (String s : arr) {
                    String sub[] = s.split(SEPARADOR_FRASES_ACENTUADAS);
                    if (sub.length == SEPARADOR_CONTADOR) {
                        acentoMap.put(sub[0], sub[1]);
                    }
                }
            } else {
                acentoMap = new HashMap<String, String>(0);
            }
        }

        String chave = str.trim().toLowerCase();
        return acentoMap.containsKey(chave) ? acentoMap.get(chave) : str;
    }

    public static boolean contemAcento(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }

        String arr[] = "âãàáäêèéëîíìïôõòóöûúùüçýÿñ".split("");
        for (String s : arr) {
            if (s.length() > 0 && str.indexOf(s) > -1) {
                return true;
            }
        }

        return false;
    }

    public static String copiarEstilo(String org, String dst) {
        if (org == null || dst == null || org.length() != dst.length()) {
            return org;
        }

        char arrOrg[] = org.toCharArray();
        char arrDst[] = dst.toCharArray();

        for (int i = 0; i < arrOrg.length; i++) {
            if (String.valueOf(arrOrg[i]).toLowerCase().equals(String.valueOf(arrOrg[i]))) {
                arrDst[i] = String.valueOf(arrDst[i]).toLowerCase().charAt(0);

            } else {
                arrDst[i] = String.valueOf(arrDst[i]).toUpperCase().charAt(0);
            }
        }

        return String.valueOf(arrDst, 0, arrDst.length);
    }

    /**
     * Remove acentos da String.
     * @param string para conversão.
     * @return a String sem acentos.
     */
    public static String removerAcento(String str) {
        /*
         * Java 1.6
        str = Normalizer.normalize(str, Normalizer.Form.NFD);  
        str = str.replaceAll("[^\\p{ASCII}]", "");
         */
        str = str.replaceAll("[ÂÀÁÄÃ]", "A");
        str = str.replaceAll("[âãàáä]", "a");
        str = str.replaceAll("[ÊÈÉË]", "E");
        str = str.replaceAll("[êèéë]", "e");
        str = str.replaceAll("[ÎÍÌÏ]", "I");
        str = str.replaceAll("[îíìï]", "i");
        str = str.replaceAll("[ÔÕÒÓÖ]", "O");
        str = str.replaceAll("[ôõòóö]", "o");
        str = str.replaceAll("[ÛÙÚÜ]", "U");
        str = str.replaceAll("[ûúùü]", "u");
        str = str.replaceAll("Ç", "C");
        str = str.replaceAll("ç", "c");
        str = str.replaceAll("[ýÿ]", "y");
        str = str.replaceAll("Ý", "Y");
        str = str.replaceAll("ñ", "n");
        str = str.replaceAll("Ñ", "N");

        return str;
    }

    /**
     * Remove caracteres especiais e acentos da String.
     * @param string para conversão.
     * @return a String sem acentos.
     */
    public static String removerCaracteresEspeciais(String str) {
        return removerAcento(str).replaceAll("['<>\\|/]", "");
    }

    /**
     * @param args the command line arguments
     */
    public static void teste() {
        System.out.println(UPPER_FIRT("upperFirt"));
        System.out.println(LOWER_FIRT("LowerFirt"));

        System.out.println(javaNomeVariavel("Java Name Parser"));
        System.out.println(removerCaracteresEspeciais("código maçã"));
        System.out.println(adicionarAcento("maca"));
        System.out.println(copiarEstilo("cOpIaR EsTiLo", "copiar estilo"));

        System.out.println(contemAcento("água"));
        System.out.println(removerExtensao("arquivo.txt"));
    }

    public static void main(String[] args) {
        teste();
    }
}
