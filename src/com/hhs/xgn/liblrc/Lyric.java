package com.hhs.xgn.liblrc;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The parsed Lyric object. <br/>
 * 
 * @author XGN
 *
 */
public class Lyric {
	/**
	 * The lyric lines
	 */
	public ArrayList<LyricLine> lines=new ArrayList<>();
	
	/**
	 * Returns the line count of the lyric.
	 * @return
	 */
	public int getLineCount(){
		return lines.size();
	}
	
	/**
	 * What is the lyric at the given time? <br/>
	 * You may need to use <code>Lyric.sort()</code> before using this function.<br/>
	 * This function runs in <code>big-O(n)</code> which is not fast <br/> 
	 * @param time - the current time of song in milliseconds.
	 * @return the lyric, or null if not found.
	 */
	public String getLyric(long time){
		
		for(int i=0;i<lines.size();i++){
			LyricLine ll=lines.get(i);
			if(!ll.isModifier && ll.getTime()>time){
				
				int x=i-1;
				while(x>=0 && lines.get(x).isModifier){
					x--;
				}
				if(x==-1){
					return null;
				}
				return lines.get(x).getOuter();
			}
		}
		
		return null;
	}
	
	/**
	 * What is the index of lyric at the given time? <br/>
	 * You may need to use <code>Lyric.sort()</code> before using this function.<br/>
	 * This function runs in <code>big-O(n)</code> which is not fast <br/> 
	 * @param time - the current time of song in milliseconds.
	 * @return the lyric, or -1 if not found.
	 */
	public int getPosition(long time){
		for(int i=0;i<lines.size();i++){
			LyricLine ll=lines.get(i);
			if(!ll.isModifier && ll.getTime()>time){
				
				int x=i-1;
				while(x>=0 && lines.get(x).isModifier){
					x--;
				}
				return x;
			}
		}
		
		return -1;
	}
	
	/**
	 * Return the modifiers.
	 * @return
	 */
	public ArrayList<LyricLine> getModifiers(){
		ArrayList<LyricLine> nw=new ArrayList<>();
		for(LyricLine ll:lines){
			if(ll.isModifier){
				nw.add(ll);
			}
		}
		
		return nw;
	}
	
	/**
	 * Sort the lyric in ascending order of time.  <br/>
	 * Modifiers come in first.
	 */
	public void sort(){
		lines.sort(new Comparator<LyricLine>() {

			@Override
			public int compare(LyricLine o1, LyricLine o2) {
				if(o1.isModifier || o2.isModifier){
					return -Boolean.compare(o1.isModifier, o2.isModifier);
				}
				return Long.compare(o1.getTime(),o2.getTime());
			}
		});
	}

	/**
	 * Return the possible length of the song <br/>
	 * This function finds the maximum time in lines <br/>
	 * @return
	 */
	public long getSongLength() {
		long x=0;
		for(LyricLine ll:lines){
			if(ll.isModifier){
				continue;
			}
			x=Math.max(ll.getTime(), x);
		}
		return x;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lyric other = (Lyric) obj;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lyric [lines=" + lines + "]";
	}
}
