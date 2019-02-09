package com.hhs.xgn.liblrc;

/**
 * The object of each lyric line. <br/>
 * Could be a modifier or a single line of lyric <br/>
 * @author XGN
 *
 */
public class LyricLine {
	public boolean isModifier;
	private String modifier;
	private String outer;
	private long time;
	
	/**
	 * Generate an empty lyric line
	 */
	public LyricLine(){
		isModifier=false;
		outer="";
		time=0;
	}
	
	/**
	 * Generate a modifier line.
	 * @param modifier
	 * @param outer
	 */
	public LyricLine(String modifier,String outer){
		isModifier=true;
		this.modifier=modifier;
		this.outer=outer;
	}
	
	/**
	 * Generate a lyric line
	 * @param time
	 * @param lyric
	 */
	public LyricLine(long time,String lyric){
		isModifier=false;
		this.time=time;
		this.outer=lyric;
	}

	/**
	 * Return the modifier. Throws LyricException if this line is not a modifier line.
	 * @return
	 */
	public String getModifier() {
		if(!isModifier){
			throw new LyricException("The line is not a modifier line!");
		}
		return modifier;
	}

	/**
	 * Set this line to Modifier line and set the modifier
	 * @param modifier
	 */
	public void setModifier(String modifier) {
		isModifier=true;
		this.modifier = modifier;
	}

	/**
	 * Get the outer text(ie. Lyrics and stuff)
	 * @return
	 */
	public String getOuter() {
		return outer;
	}

	/**
	 * Set the outer text(ie. Lyrics and stuff)
	 * @return
	 */
	public void setOuter(String outer) {
		this.outer = outer;
	}

	/**
	 * Get the time of this line if it is a lyric one. <br/>
	 * Throws LyricException if it isn't.
	 * @return
	 */
	public long getTime() {
		if(isModifier){
			throw new LyricException("This line is not a lyric line!");
		}
		return time;
	}

	/**
	 * Set the line to a lyric line and set the time.
	 * @param time
	 */
	public void setTime(long time) {
		isModifier=false;
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isModifier ? 1231 : 1237);
		result = prime * result + ((modifier == null) ? 0 : modifier.hashCode());
		result = prime * result + ((outer == null) ? 0 : outer.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
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
		LyricLine other = (LyricLine) obj;
		if (isModifier != other.isModifier)
			return false;
		if (modifier == null) {
			if (other.modifier != null)
				return false;
		} else if (!modifier.equals(other.modifier))
			return false;
		if (outer == null) {
			if (other.outer != null)
				return false;
		} else if (!outer.equals(other.outer))
			return false;
		if (time != other.time)
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(isModifier){
			return "ModifierLine [modifier=" + modifier + ", outer=" + outer+ "]";
		}else{
			return "LyricLine [time=" + time + ", outer=" + outer+ "]";
		}
	}
	
	
}
