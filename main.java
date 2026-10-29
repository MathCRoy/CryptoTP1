import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {

    private static final Map<Character, Integer> LETTRE_VERS_NOMBRE = new HashMap<>();
    private static final Map<Integer, Character> NOMBRE_VERS_LETTRE = new HashMap<>();

    static {
        for (int i = 0; i < 26; i++) {
            char lettre = (char) ('A' + i);
            LETTRE_VERS_NOMBRE.put(lettre, i);
            NOMBRE_VERS_LETTRE.put(i, lettre);
        }
    }

    private static final List<Integer> AFFINE_MULTIPLICATEURS = List.of(1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25);
    private static final int[][] HILL_KEY = { { 3, 4 }, { 6, 3 } };
    private static final double IC_FRANCAIS = 0.0746;

    private static final String MESSAGE_CHIFFRE_1 =
            "WPZIJNYDVNEZINVDNKVNNDOPKVMGZNAMVIXVDNJPZNKVBIJGHVDNXZLPZEZNVDNXZNOLPZOPQZ"
            + "PSJWOZIDMPIZWJIIZIJOZVXZKMZHDZMOMVQVDGKMVODLPZGZNIJOZNNJIODHKJMOVIOZNXZMOZN"
            + "HVDNXZIZNOKVNXZLPDXJHKOZGZKGPNGZNNZIODZGXZNOYZKMZIYMZKGVDNDMVXZLPZOPAVDNZIO"
            + "JPOXVNHJDEVDYPAPIVZXMDMZXZOKCDCDVGJMNKMZIYNPIZBMVIYZDINKDMVODJIXVMOPZIVPMVN"
            + "WZNJDIKJPMYZXCDAAMZMGZKMJXCVDIHZNNVBZOMJDNNZXJIYZNYZQMVDZIONPAADMZ";

    private static final String MESSAGE_CHIFFRE_2 =
            "VWBGZBITZSATKVVGGRETVWTSVUTATLQVUUVGTPPTISWTZHYBZTZEBISZTHBGZTGVLMLGTSVPTZL"
            + "GTNLTSLVEVIHTGVZAVIZWTZTSVUTZLIUTLHBPPTWTIRETVLATARMMRHLWSTATZSUATHTHBLGZAV"
            + "RWWTLGZRWJVHBPORTIATSVUTZAVIZHTUGTPRTGTQTGHRHT";

    private static final String MESSAGE_3 = "JAMAISTROISSANSQUATREHAHAX";

    private static final String MESSAGE_4 = "LAFINESTPROCHECENESTPASLEMOMENTDABANDONNER";

    private static final String MESSAGE_5 =
            "WCEFZWSGTUKYFVSDAMBQENAQCDEAZSIAEPXMGTEFRMUEEFPEWGRVYFSRUPUYDUECREWJIEGVSUITKVQV"
            + "TRTSBTERZNSJPRXIELEIUYGVNNAVSQAHZEBKAQKGVZFSXIFVTPNMTWRRXPSJDVLJSIEAZWAVSFGKSJJR"
            + "YYWJVEGMAVNGLMSIDRBSIJJNPSIKEFOQDCEZKRHHURRUIVSYOKBVSCUYFRLYURUVRYKXSOTRSEWJJRZM"
            + "SESNRIFVPRZIFAEFAMGMRNOQSETSOIFUEIUYGAEYKWCLLVMRSRNBAZSRUCUYFHURSSBDEFYEUVSBOXPZ"
            + "EAIPOZRBAMXVSHOWTZEEJIJFUFRIGXAEYIHCEFMSG";

    private static final char[][] POLYBE = {
            { 'C', 'R', 'Y', 'P', 'T' },
            { 'G', 'L', 'O', 'A', 'B' },
            { 'D', 'E', 'F', 'H', 'I' },
            { 'J', 'K', 'M', 'N', 'Q' },
            { 'S', 'U', 'V', 'X', 'Z' }
    };

    public static void main(String[] args) {
        new main().dechiffrementDecalage();
        System.out.println();
        System.out.println("==============================");
        System.out.println("Indice 1 = 3 secondes, avec i = 5");
        System.out.println("==============================");
        System.out.println();

        new main().dechiffrementAffine();
        System.out.println();
        System.out.println("==============================");
        System.out.println("Indice 2 = Nombre d'exercices = 3, avec c(11,3)");
        System.out.println("==============================");
        System.out.println();

        new main().dechiffrementHill();
        System.out.println();
        System.out.println("==============================");
        System.out.println("Indice 3 = Aucun mot de 4 lettres trouvé :S");
        System.out.println("==============================");
        System.out.println();

        new main().dechiffrementPolybe();
        System.out.println();
        System.out.println("==============================");
        System.out.println("Indice 4 = 6");
        System.out.println("==============================");
        System.out.println();



        new main().dechiffrementVigenere();
        System.out.println();
        System.out.println("==============================");
        System.out.println("Indice 5 = 7");
        System.out.println("==============================");
        System.out.println();
    }

    private void dechiffrementDecalage(){
        System.out.println("==============================");
        System.out.println("Déchifrement décalage");
        System.out.println("==============================");
        for (int i = 0; i < 26; i++) {
            System.out.println("i = " + i);
            String essaieMsgDechiffre = "";
            for (char c : MESSAGE_CHIFFRE_1.toCharArray())
                essaieMsgDechiffre += NOMBRE_VERS_LETTRE.get((LETTRE_VERS_NOMBRE.get(c) + i) % 26);
            System.out.println(essaieMsgDechiffre);
        }
    }

    private void dechiffrementAffine() {
        System.out.println("==============================");
        System.out.println("Déchifrement affine");
        System.out.println("==============================");
        for (int a : AFFINE_MULTIPLICATEURS) {
            for (int b =0; b < 26; b++) {
                String essaieMsgDechiffre = "";
                System.out.println(String.format("c(%d,%d)", a, b));
                for (char c : MESSAGE_CHIFFRE_2.toCharArray())
                    essaieMsgDechiffre += NOMBRE_VERS_LETTRE.get((LETTRE_VERS_NOMBRE.get(c) * a + b) % 26);
                System.out.println(essaieMsgDechiffre);
            }
        }
    }

    private void dechiffrementHill() {
        System.out.println("==============================");
        System.out.println("Déchifrement Hill");
        System.out.println("==============================");

        for (int i = 0; i < MESSAGE_3.length(); i +=2 ){
            int m1 = LETTRE_VERS_NOMBRE.get(MESSAGE_3.charAt(i)); 
            int m2 = LETTRE_VERS_NOMBRE.get(MESSAGE_3.charAt(i+1)); 
            char c1 = NOMBRE_VERS_LETTRE.get((m1 * HILL_KEY[0][0] + m2 * HILL_KEY[1][0]) % 26);
            char c2 = NOMBRE_VERS_LETTRE.get((m1 * HILL_KEY[0][1] + m2 * HILL_KEY[1][1]) % 26);
            System.out.println(c1 + " " + c2);
        }
    }

    private void dechiffrementPolybe() {
        Map<Integer, Integer> numberCount = new HashMap<>();
        for (int i = 1; i <= 5; i++)
            numberCount.put(i,0);

        for (char c : MESSAGE_4.toCharArray()){
            chercher:
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 5; j++){
                    if (c == POLYBE[i][j]){
                        numberCount.put(i+1, numberCount.get(i+1)+1);
                        numberCount.put(j+1, numberCount.get(j+1)+1);
                        break chercher;
                    }
                }
            }
        }

        int maxUtilisations = Collections.max(numberCount.entrySet(), Map.Entry.comparingByValue()).getValue();
        int sommeChiffresUtilise = 0;
        for (int i = 1; i <= 5; i++)
            if (numberCount.get(i) == maxUtilisations)
                sommeChiffresUtilise += i;

        System.out.println("Nombre Plus Utilisé Polybe: " + sommeChiffresUtilise);

    }

    private void dechiffrementVigenere(){
        // Kasiki, get map of 3 repeated char or more (I decided to stop at 10 char) with their positions;
        Map<String, Integer> kasikiMap = new HashMap<>();
        for (int i = 3; i <= 10; i++){
            for (int j = 0; j <= MESSAGE_5.length() - i ; j++) {
                String testedSubString = MESSAGE_5.substring(j,j+i);
                kasikiMap.put(testedSubString, (kasikiMap.get(testedSubString) == null) ? 1 : kasikiMap.get(testedSubString) + 1 );
            }
        }
        kasikiMap.values().removeIf(value -> value <= 1);

        Map<Integer, Integer> diviseursCount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : kasikiMap.entrySet()){
            int pos1 = MESSAGE_5.indexOf(entry.getKey());
            int pos2 = MESSAGE_5.indexOf(entry.getKey(), pos1 + 1);
            int diff = pos2 - pos1;
            System.out.println(entry.getKey() + " distance " + diff);
            for (int d : diviseurs(diff)){
                if (d > 2)
                    diviseursCount.merge(d, 1, Integer::sum);
            }
        }
        int maxCount = Collections.max(diviseursCount.values());
        for (Map.Entry<Integer, Integer> entry : diviseursCount.entrySet())
            if (entry.getValue() == maxCount)
                System.out.println(entry.getKey() + " est un diviseur le plus commun (" + maxCount + ") fois");


        System.out.println();
        System.out.println("Kasiki suggère une clé de 3 ou 6 caractères");
        System.out.println("==============================");
        System.out.println();

        // Calculate the different IC for different key lenghts. Including i = 0 to 2 for code simplicity, even though I did kasiki on 3 to 10
        Map<Integer, Double> icParKeyLength = new HashMap<>();
        for (int keyLength = 1; keyLength <= 10; keyLength++) {
            double sumIC = 0;
            for (int column = 0; column < keyLength; column++) {
                List<Integer> charsOfColumn = new ArrayList<>();
                for (int j = column; j < MESSAGE_5.length(); j += keyLength)
                    charsOfColumn.add(LETTRE_VERS_NOMBRE.get(MESSAGE_5.charAt(j)));
                sumIC += indiceCoincidence(charsOfColumn);
            }
            icParKeyLength.put(keyLength, sumIC / keyLength);
            System.out.println("Pour key lenght de :" + keyLength + " on a IC moyen de :" + sumIC / keyLength);
        }
        int keyLengthProbable = Collections.min(icParKeyLength.keySet(),
                Comparator.comparingDouble(k -> Math.abs(icParKeyLength.get(k) - IC_FRANCAIS)));
        System.out.println(keyLengthProbable + " est la key length dont l'IC est le plus proche du français");

        System.out.println();
        System.out.println("IC suggère une clé de 6 caractères. Conclusion -> key lenght = 6");
        System.out.println("==============================");
        System.out.println();

        //Create a bunch of subtexts that grouped by position + 6
        Map<Integer, List<Character>> carsAPositionIDuGoupe = new HashMap<>();
        for (int i = 0; i < 6; i++){
            for (int j = i; j < MESSAGE_5.length(); j += 6)
                carsAPositionIDuGoupe.computeIfAbsent(i, k -> new ArrayList<>()).add(MESSAGE_5.charAt(j));
        }

        //Find the most common char of each subgroups of char, it's most likely e. Let's keep the difference between e and the most common letter
        Map<Integer, Integer> differenceAvecEParGroupe = new HashMap<>();
        for (Map.Entry<Integer, List<Character>> groupe : carsAPositionIDuGoupe.entrySet()) {
            Map<Character, Integer> occurrencesParCar = new HashMap<>();
            for (char c : groupe.getValue())
                occurrencesParCar.merge(c, 1, Integer::sum);
            char carLePlusFrequent = Collections.max(occurrencesParCar.entrySet(), Map.Entry.comparingByValue()).getKey();
            int differenceAvecE = (LETTRE_VERS_NOMBRE.get(carLePlusFrequent) - LETTRE_VERS_NOMBRE.get('E') + 26) % 26;
            differenceAvecEParGroupe.put(groupe.getKey(), differenceAvecE);
            System.out.println("Groupe " + groupe.getKey() + " : " + carLePlusFrequent + " le plus fréquent, différence avec E : " + differenceAvecE);
        }

        System.out.println();
        System.out.println("La clé suggérée est ORANVI");
        System.out.println("==============================");
        System.out.println();

        //Apply key to OG text
        String cle = "ORANVI";
        String messageDechiffre = "";
        for (int charPos = 0; charPos < MESSAGE_5.length(); charPos++) {
            int c = LETTRE_VERS_NOMBRE.get(MESSAGE_5.charAt(charPos));
            int k = LETTRE_VERS_NOMBRE.get(cle.charAt(charPos % cle.length()));
            messageDechiffre += NOMBRE_VERS_LETTRE.get((c - k + 26) % 26);
        }
        System.out.println(messageDechiffre);

        System.out.println();
        System.out.println("ORANVI donne seulement des bouts de mots. ça ressemble à ORANGE");
        System.out.println("==============================");
        System.out.println();

        cle = "ORANGE";
        messageDechiffre = "";
        for (int charPos = 0; charPos < MESSAGE_5.length(); charPos++) {
            int c = LETTRE_VERS_NOMBRE.get(MESSAGE_5.charAt(charPos));
            int k = LETTRE_VERS_NOMBRE.get(cle.charAt(charPos % cle.length()));
            messageDechiffre += NOMBRE_VERS_LETTRE.get((c - k + 26) % 26);
        }
        System.out.println(messageDechiffre);


    }

    private List<Integer> diviseurs(int n){
        List<Integer> diviseurs = new ArrayList<>();
        for (int i = 1; i <= Math.abs(n); i++)
            if (n % i == 0)
                diviseurs.add(i);
        return diviseurs;
    }

    private double indiceCoincidence(List<Integer> valeurs){
        double nombreDePaires = valeurs.size() * (valeurs.size() - 1.0);
        if (nombreDePaires == 0)
            return 0;

        Map<Integer, Integer> occurrencesParValeur = new HashMap<>();
        for (int valeur : valeurs)
            occurrencesParValeur.merge(valeur, 1, Integer::sum);

        double nombreDePairesIdentiques = 0;
        for (int occurrences : occurrencesParValeur.values())
            nombreDePairesIdentiques += occurrences * (occurrences - 1.0);

        return nombreDePairesIdentiques / nombreDePaires;
    }

}
