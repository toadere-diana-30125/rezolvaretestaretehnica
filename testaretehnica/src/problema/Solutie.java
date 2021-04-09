package problema;

public class Solutie {
    String s;

    // numărul de adăugări care sunt necesare
    int numaradaugari;

    // numărul de stergeri care sunt necesare
    int numarStergeri;

    // secventele pe care le contorizam
    int[] seq;


    public  int strongPasswordChecker(String s) {
        if (s == null || s.equals("")) return 6;
        this.s = s;

        // Inițializăm variabilele
        numaradaugari = 0;
        numarStergeri = 0;
        seq = new int[s.length() + 1];

        // Contorizăm numărul de  "adăugări" si frecvența secvențelor întalnite
        readString();

        // Folosirea ștergerilor pentru a minimiza pauzele necesare
        if (s.length() > 20) spendDeletions();

        // numărul de pauze de secvență necesare
        int numpauze = 0;
        for (int i = 3; i < seq.length; i++) {
            numpauze += seq[i] * (i / 3);
        }

        // consolidarea pauzelor ,adăugărilor si modificărilor
        int numschimbari = Math.max(numpauze, numaradaugari);

        // pentru secvențe scurte ,este necesară consolidarea prin inserții si modificări.
        if (s.length() < 6) {
            int numarinsertii = 6 - s.length();
            numschimbari = Math.max(numarinsertii, numschimbari);
        }

        // Pentru secvențe prea lungi, adăugăm numărul de ștergeri si adăugări necesare
        // în variabila numschimbari.
        if (s.length() > 20) {
            numschimbari = numarStergeri + numschimbari;
        }

        return numschimbari;
    }

    /**
     Se procesează sirul dat stocand dacă sirul îndeplineste cerintele si anume daca se repeta anumite caractere si stocheaza
     secventele de caractere repetate si daca lungimea secventei are mai mult de 3 caractere
     */
    private void readString() {
        boolean lipsanumar = true;
        boolean lipsamajuscula = true;
        boolean lipsaliteramica = true;

        // Lungimea secvenței
        int c = 1;
        char tmp = s.charAt(0);
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                // Secvența continuă
                if (s.charAt(i) == tmp) c++;

                    // Sfarșitul secvenței
                else {
                    if (c > 2) seq[c]++;
                    c = 1;
                    tmp = s.charAt(i);
                }
            }
            if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') lipsaliteramica = false;
            else if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') lipsamajuscula = false;
            else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') lipsanumar = false;
        }

        // gestionarea secvențelor lungi care continuă pana la sfarșitul șirului dat.
        if (c > 2) seq[c]++;

        if (lipsaliteramica) numaradaugari++;
        if (lipsamajuscula) numaradaugari++;
        if (lipsanumar) numaradaugari++;
    }

   //Ștergerile
    private void spendDeletions() {
        numarStergeri = s.length() - 20;
        int ndtemp = numarStergeri;
        int lastThreeMult = 3 * ((seq.length - 1) / 3);
        for (int i = lastThreeMult; i < lastThreeMult + 3; i++) {

            int j = (i >= seq.length) ? i - 3: i;
            while (j > 2 && ndtemp > 0) {
                if (seq[j] > 0) {

                    // Avem o secvență mai mică de lungime j
                    seq[j]--;

                    /*
                     * Stabilim dacă mai avem suficiente ștergeri
                     * pentru a reduce numărul de secvențe necesare

                     * dacă nu mai avem ștergeri, le folosim doar cele pe care le aveam.

                     */


                    int d = Math.min((i % 3) + 1, ndtemp);

                    // avem încă o secvență de lungime (j-d)
                    seq[j-d]++;

                    // analizăm dacă mai avem ștergeri rămase
                    ndtemp -= d;
                }
                else j -= 3;
            }
        }
    }


    public static void main(String[] args) {
        Solutie sol = new Solutie();

        System.out.println(sol.strongPasswordChecker("890765478"));
        // 2 schimbări sau adăugări; lipsește majusculă și literă mică

        System.out.println(sol.strongPasswordChecker("serhbdhjshfhnsnchshjmsdjndjmshkandhjv"));
        // 19; 17 ștergeri  si 2 schimbări; lipseste numar și lipsește litera mică;

        System.out.println(sol.strongPasswordChecker("ABLMOPQRSTVIWXYZGDEFGHIJ"));
        // 6; 4 ștergeri si 2 schimbări; trebuie să adăugăm literă mică și un număr

        System.out.println(sol.strongPasswordChecker(""));
        // 6 inserții

        System.out.println(sol.strongPasswordChecker("kkkk"));
        // 2  adăugăm un număr după al doilea "k" sau o majusculă,iar după cea de-a 4-a literă putem adăuga un caracter
        //ce nu l-am folosit anterior

        System.out.println(sol.strongPasswordChecker("bbbbbbbbbbbbbbbbbbbbbbb"));
        // 9  3 ștergeri, avem nevoie de schimbări din 'b',care pot consta în majuscule și litere mici,a.î
        //după două litere "b" să urmeze alt caracter

        System.out.println(sol.strongPasswordChecker("..."));
        // 3  ,trebuie adăugate o litera mică,un număr și o majusculă

        System.out.println(sol.strongPasswordChecker("Ab1B2bC3D6B9R8Rgc8Pp8R3T5vFU0L"));
        // 10 ( 10 ștergeri)

        System.out.println(sol.strongPasswordChecker("5555555555aaaC00000000"));
        // 7

        System.out.println(sol.strongPasswordChecker("aaaabbaaabbaaa123456A"));
        // 3

        System.out.println(sol.strongPasswordChecker("8888888888888888888888888"));
        // 11

        System.out.println(sol.strongPasswordChecker("bbb222"));
        // 2

        System.out.println(sol.strongPasswordChecker("456389017823bbbbbg"));
        // 1

        System.out.println(sol.strongPasswordChecker("CCCCCCBBBBBB0234788900a"));
        // 5

    }

}