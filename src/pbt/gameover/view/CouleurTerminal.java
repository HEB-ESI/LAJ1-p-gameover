package pbt.gameover.view ;

/**
 * <p>CouleurTerminal met à disposition un ensemble de constantes permettant de
 * colorer les sorties "stdout". </p>
 *
 * <p>Ca s'utilise par exemple comme suit
 * <code>System.out.println(CouleurTerminal.RED + "j'écris en rouge" +
 * CouleurTerminal.DEFAULT);</code>.
 */
public enum CouleurTerminal {
    	DEFAULT("\033[0m"),
	    BLACK("\033[30m"),
	    RED("\033[31m"),
	    GREEN("\033[32m"),
	    YELLOW("\033[33m"),
	    BLUE("\033[34m"),
	    PINK("\033[35m"),
	    LIGHT_BLUE("\033[36m"),
	    WHITE("\033[37m"),
	    GREY("\033[38m"),
	    ; 

	private String couleur;

	private CouleurTerminal (String c) {
		this.couleur = c ; 
	}

	public String toString() {
		return couleur ;
	}

	public static void main ( String[] args ) { 
	    for (CouleurTerminal ct : CouleurTerminal.values()) 
		System.out.println("Couleur: " + ct +"essai\033[0m" );
	     }

}

