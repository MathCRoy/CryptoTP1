import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Exercice3 {

    public static void run() {
        partie1();
        partie2();
    }

    private static void partie1() {

        try {

            System.out.println();
            System.out.println("==============================");
            System.out.println("Ex 3.1 started");
            System.out.println("==============================");
            System.out.println();

            // GEt AES key
            byte[] fileBytes = Files.readAllBytes(Path.of("image_secrete.bmp"));
            byte[] fileHeader = Arrays.copyOfRange(fileBytes, 0, 54);
            byte[] fileContent = Arrays.copyOfRange(fileBytes, 54, fileBytes.length);

            byte[] cle = new byte[16];
            // here, cle is edited in the constructors, a weird case when an object is not passed by copy.
            // We don't use the returned objects here
            new SecureRandom().nextBytes(cle);

            SecretKey cleAES = new SecretKeySpec(cle, "AES");

            //===========================

            // ECB
            Cipher ecb = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ecb.init(Cipher.ENCRYPT_MODE, cleAES);
            // Image, encrypted with AES-128 in ECB.
            byte[] chiffrementEcb = ecb.doFinal(fileContent);

            // ECB image
            ByteArrayOutputStream ecbOutput = new ByteArrayOutputStream();
            ecbOutput.write(fileHeader);
            ecbOutput.write(chiffrementEcb);
            Files.createDirectories(Path.of("EX3Output"));
            Files.write(Path.of("EX3Output/image_ecb.bmp"), ecbOutput.toByteArray());

            //===========================

            // CBC
            byte[] vecteurInitialisation = new byte[16];
            new SecureRandom().nextBytes(vecteurInitialisation);
            Cipher cbc = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cbc.init(Cipher.ENCRYPT_MODE, cleAES, new IvParameterSpec(vecteurInitialisation));
            // Image, encrypted with AES-128 in CBC.
            byte[] chiffrementCbc = cbc.doFinal(fileContent);

            // CBC Image
            ByteArrayOutputStream cbcOutput = new ByteArrayOutputStream();
            cbcOutput.write(fileHeader);
            cbcOutput.write(chiffrementCbc);
            Files.write(Path.of("EX3Output/image_cbc.bmp"), cbcOutput.toByteArray());

            System.out.println("Looking at the results, the EBC is image is tougher to read but still readable");
            System.out.println("The CBC image is straight up noise");

            System.out.println();
            System.out.println("==============================");
            System.out.println("Ex 3.1 Finished");
            System.out.println("==============================");
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void partie2() {
        
        try {

            System.out.println();
            System.out.println("==============================");
            System.out.println("Ex 3.2 started");
            System.out.println("==============================");
            System.out.println();

            // Get the list of strings
            List<String> ligneFichierdefi = Files.readAllLines(Path.of("defi.txt"));
            byte[] bytesC1 = HexFormat.of().parseHex(ligneFichierdefi.get(0));
            byte[] bytesC2 = HexFormat.of().parseHex(ligneFichierdefi.get(1));

            // Apply cXor on the bytes
            byte[] cXor = new byte[Math.min(bytesC1.length, bytesC2.length)];
            for (int i = 0; i < cXor.length; i++)
                cXor[i] = (byte) (bytesC1[i] ^ bytesC2[i]);

            essayerCrib(cXor, " que");

            System.out.println("que Suggère ------------ dit le ------------------------------s la---------------------------- ");

            essayerCrib(cXor, " le");

            System.out.println("Suggère : ------------ dit le ------------------------------s la----------------------------");
            System.out.println("Suggère : ----------ie---- qu-vol----------fra---------------qua-------dao------------------");

            essayerCrib(cXor, " dit");

            System.out.println("Suggère : ------------ dit que------------------------------ que vol------------------------");
            System.out.println("Suggère : ------------ que le ------------------------------s la je ------------------------");

            essayerCrib(cXor, " francais");

            System.out.println("francais ne marche pas");

            // essayerCrib(cXor, " de ");
            // essayerCrib(cXor, " la ");
            // essayerCrib(cXor, " et ");
            // essayerCrib(cXor, " les ");
            // essayerCrib(cXor, " des ");
            // essayerCrib(cXor, " en ");
            essayerCrib(cXor, " un ");
            // essayerCrib(cXor, " une ");
            essayerCrib(cXor, " du ");

            System.out.println("Suggère : ------------ dit que---------------------- du ---- que vol--------- un -----------");
            System.out.println("Suggère : ------------ que le ----------------------ipub----s la je ---------e1eu-----------");

            essayerCrib(cXor, " est ");

            System.out.println("Suggère : ------------ dit que---------------- est - du ---- que vol--------- un -----------");
            System.out.println("Suggère : ------------ que le ---------------- nuit-ipub----s la je ---------e1eu-----------");

            // essayerCrib(cXor, " jour ");
            // essayerCrib(cXor, " pour ");
            // essayerCrib(cXor, " qui ");
            // essayerCrib(cXor, " dans ");
            // essayerCrib(cXor, " pas ");
            // essayerCrib(cXor, " plus ");
            // essayerCrib(cXor, " par ");
            // essayerCrib(cXor, " sur ");
            // essayerCrib(cXor, " au ");
            essayerCrib(cXor, " mais ");

            System.out.println("Suggère : ------------ dit que---------------- est - du dant que vol--------- un -----------");
            System.out.println("Suggère : ------------ que le ---------------- nuit-ipub mais la je ---------e1eu-----------");

            essayerCrib(cXor, " pendant ");

            System.out.println("Suggère : ------------ dit que---------------- est - pendant que vol--------- un -----------");
            System.out.println("Suggère : ------------ que le ---------------- nuit-ide, mais la je ---------e1eu-----------");

            essayerCrib(cXor, " il dit ");
            // essayerCrib(cXor, " elle dit ");

            System.out.println("Suggère : --------- il dit que---------------- est - pendant que vol--------- un -----------");
            System.out.println("Suggère : ---------5-- que le ---------------- nuit-ide, mais la je ---------e1eu-----------");

            essayerCrib(cXor, " il dit que le ");
            essayerCrib(cXor, " que le vol a eu");

            System.out.println("Suggère : --------- il dit que le gran-------- est - pendant que vol--------- un -----------");
            System.out.println("Suggère : ---------5-- que le vol a eu-------- nuit-ide, mais la je ---------e1eu-----------");

            essayerCrib(cXor, " il dit que le grand ");

            System.out.println("Suggère : --------- il dit que le grand ------ est - pendant que vol--------- un -----------");
            System.out.println("Suggère : ---------5-- que le vol a eu l------ nuit-ide, mais la je ---------e1eu-----------");

            essayerCrib(cXor, " que le vol a eu lieu la ");

            // Swapped: the two messages exchange roles after position for the later part
            System.out.println("Suggère : --------- il dit que le grand coffre est vide, mais la je ---------e1eu-----------");
            System.out.println("Suggère : ---------5-- que le vol a eu lieu la nuit, pendant que vol--------- un -----------");

            // essayerCrib(cXor, " nombre ");
            // essayerCrib(cXor, " combinaison ");
            // essayerCrib(cXor, " cle ");
            // essayerCrib(cXor, " pendant que volleur ");
            // essayerCrib(cXor, " la je ne ");
            essayerCrib(cXor, " police ");

            System.out.println("Suggère : --------- il dit que le grand coffre est vide, mais la police -----e1eu-----------");
            System.out.println("Suggère : ---------5-- que le vol a eu lieu la nuit, pendant que le gard----- un -----------");

            essayerCrib(cXor, " le gardien ");

            System.out.println("Suggère : --------- il dit que le grand coffre est vide, mais la police dout-e1eu-----------");
            System.out.println("Suggère : ---------5-- que le vol a eu lieu la nuit, pendant que le gardien - un -----------");

            // essayerCrib(cXor, " la police doute qu");
            essayerCrib(cXor, " pendant que le gardien de nuit");

            System.out.println("Suggère : --------- il dit que le grand coffre est vide, mais la police doute de sa---------");
            System.out.println("Suggère : ---------5-- que le vol a eu lieu la nuit, pendant que le gardien de nuit---------");

            // essayerCrib(cXor, "de, mais la police doute de sa parole");
            essayerCrib(cXor, ", pendant que le gardien de nuit dormait");

            System.out.println("Suggère : --------- il dit que le grand coffre est vide, mais la police doute de sa version.");
            System.out.println("Suggère : ---------5-- que le vol a eu lieu la nuit, pendant que le gardien de nuit dormait.");

            // essayerCrib(cXor, " appel");
            // essayerCrib(cXor, " cherche");
            // essayerCrib(cXor, " argent");
            // essayerCrib(cXor, "h45");
            // essayerCrib(cXor, " camera");
            essayerCrib(cXor, " suspect");

            System.out.println("Suggère : -- suspect-- dit que le grand coffre est vide, mais la police doute de sa version.");
            System.out.println("Suggère : -- est cla-- que le vol a eu lieu la nuit, pendant que le gardien de nuit dormait.");

            essayerCrib(cXor, "Le suspect a dit");

            System.out.println("Suggère : Le suspect a dit que le grand coffre est vide, mais la police doute de sa version.");
            System.out.println("Suggère : Il est clair que le vol a eu lieu la nuit, pendant que le gardien de nuit dormait.");

            System.out.println();
            System.out.println("==============================");
            System.out.println("Ex 3.2 finished");
            System.out.println("==============================");
            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Try crib over every position of cXor
    private static void essayerCrib(byte[] cXor, String cribText) {
        System.out.println();
        System.out.println("avec : " + cribText);
        byte[] crib = cribText.getBytes(StandardCharsets.UTF_8);
        for (int position = 0; position <= cXor.length - crib.length; position++) {
            byte[] resultat = new byte[crib.length];
            for (int cribCharPosition = 0; cribCharPosition < crib.length; cribCharPosition++) {
                resultat[cribCharPosition] = (byte) (cXor[position + cribCharPosition] ^ crib[cribCharPosition]);
            }
            // here, if one message has the crib, the other is readable. We don't know if it's in m1 or m2.
            System.out.println(position + " : " + new String(resultat, StandardCharsets.UTF_8));
        }
        System.out.println();
    }

}
