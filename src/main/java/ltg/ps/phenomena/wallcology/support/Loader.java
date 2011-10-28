/*
 * Created Jan 18, 2011
 */
package ltg.ps.phenomena.wallcology.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ltg.ps.phenomena.wallcology.Wallcology;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class Loader {

	/**
	 * TODO Description
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String win = null;
		String conf = null;
		try {
			win = in.readLine();
			in.readLine();
			conf = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(win != null)
			System.out.println(win);
		if (conf!=null)
			System.out.println(conf);
		
		Wallcology w = new Wallcology("wc_dev");
		//w.restore();
		w.configureWindows(win);
		w.configure(conf);
		//for(Wall m : w.getWalls())
			//System.out.println(m);
			
		System.out.println(w.toXML());
				
		//w.prova();
	}

}
