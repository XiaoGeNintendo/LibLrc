package test.hhs.xgn.liblrc;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

import com.hhs.xgn.liblrc.LibLrc;
import com.hhs.xgn.liblrc.Lyric;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Test {
	public static void main(String[] args) throws IOException, JavaLayerException {
		
		Lyric l=LibLrc.load(new File("test.lrc"));
		l.sort();
		
		System.out.println(l);
		
		JLabel jl=new JLabel("Lyric ");
		jl.setFont(new Font("PixelMplus12", 0, 20));
		jl.setHorizontalAlignment(SwingConstants.CENTER);;
		JFrame ll=new JFrame("Lyric");
		ll.add(jl);
		ll.setSize(600, 200);
		ll.setVisible(true);
		ll.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		File file=new File("test.mp3");
		FileInputStream fis=new FileInputStream(file);
		BufferedInputStream stream=new BufferedInputStream(fis);
		Player player=new Player(stream);
		Thread shit=new Thread(){
			
			int curPos=-1;
			public void run(){
				long time=System.currentTimeMillis();
				while(true){
					long delta=System.currentTimeMillis()-time;
					int newPos=l.getPosition(delta);
					if(newPos!=curPos){
						curPos=newPos;
						
						String s=l.getLyric(delta);
						for(int i=0;i<s.length();i++){
							jl.setText(s.substring(0, i+1)+" ");
							try {Thread.sleep(50);}catch (InterruptedException e) {}
						}
						
					}
					
					if(delta%2000>=1000){
						jl.setText(jl.getText().substring(0, jl.getText().length()-1)+" ");
					}else{
						jl.setText(jl.getText().substring(0, jl.getText().length()-1)+"|");
					}
					
				}
			}
		};
		
		shit.start();
		player.play();
		
		
	}
}
