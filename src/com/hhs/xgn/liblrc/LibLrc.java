package com.hhs.xgn.liblrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lib Lrc is a easy library for parsing .lrc files. <br/>
 * The usage is quite simple. <br/>
 * Use <code>LibLrc.load()</code> to load from a Lrc File or a Lrc String<br/> 
 * @author XGN
 *
 */
public class LibLrc {
	/**
	 * Load a lyric from the given string.
	 * @param lrc - the lrc string
	 * @return the Lyric instance
	 */
	public static Lyric load(String lrc){
		Lyric l=new Lyric();
		String[] lines=lrc.split("\n");
		
		int line=1;
		for(String ls:lines){
			if(!ls.matches("\\[.+\\].*")){
				throw new LyricException("On line "+line+":Parse Error");
			}
			
			try{
				String inside=ls.substring(ls.indexOf('[')+1, ls.indexOf(']'));
				String outside=ls.substring(ls.indexOf(']')+1);
				
				if(inside.matches("[0-9]+:[0-9]+.[0-9]+")){ 
					//A number string
					int minute=Integer.parseInt(inside.substring(0,inside.indexOf(':')));
					int second=Integer.parseInt(inside.substring(inside.indexOf(':')+1,inside.indexOf('.')));
					int ms=Integer.parseInt(inside.substring(inside.indexOf('.')+1));
					
					l.lines.add(new LyricLine(minute*60000+second*1000+ms*10,outside));
				}else{
					//A modifier
					l.lines.add(new LyricLine(inside,outside));
				}
			}catch(Exception e){
				throw new LyricException("On line "+line+":Exception occurred"+e);
			}
			
			line++;
		}
		return l;
	}
	
	/**
	 * Load the lyric from a file. <br/>
	 * Note: will read the file in default format! <br/>
	 * @param f
	 * @return
	 * @throws IOException 
	 */
	public static Lyric load(File f) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		try{
			String tot="",lne;
			while((lne=br.readLine())!=null){
				tot+=lne+"\n";
			}
			
			return load(tot);
		}catch(Exception e){
			throw new LyricException("Load error: nested Exception "+e);
		}finally{
			br.close();
		}
	}
	
	/**
	 * Load the lyric from a file. <br/>
	 * 
	 * @param f
	 * @param charset
	 * @return
	 * @throws IOException 
	 */
	public static Lyric load(File f,String charset) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f),charset));
		try{
			String tot="",lne;
			while((lne=br.readLine())!=null){
				tot+=lne+"\n";
			}
			
			return load(tot);
		}catch(Exception e){
			throw new LyricException("Load error: nested Exception "+e);
		}finally{
			br.close();
		}
	}
}
