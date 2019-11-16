package com.tondeuse;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;

import org.springframework.stereotype.Component;

@Component
public class Lawn {

	private ArrayList<Mower> mowers;

    public Point getAreaSize() {
        return areaSize;
    }

    private Point areaSize ;

    public Lawn() {}

    public Lawn(Point areaSize){
		this.areaSize = areaSize;
		this.mowers = new ArrayList<>();
	}

	public Lawn (Point areaSize, ArrayList<Mower> mowers) {
		this(areaSize);
		this.mowers = mowers;
	}

	public Object mow() {
		String result = "";
		if(mowers.size() == 0) {
			return "Rien a tondre!";
		} 
		
		for (Mower mower : mowers) {
			int x = (int) mower.getPosition().getX();
			int y = (int) mower.getPosition().getY();
			String orientation = mower.getPosition().getOrientation();
			for (String move : mower.getMoves()) {
				switch (move) {
				case "A":
					System.out.print("Deplacement avant");
					switch (orientation) {
					case "N":
						System.out.println("vers le nord");
						if(y+1>areaSize.getY()) {
							continue;
						}
						y++;
						break;
					case "E":
						System.out.println(" vers est");
						if(x+1>areaSize.getX()) {
							continue;
						}
						x++;
						break;
					case "W":
						System.out.println(" vers ouest");
						if(x-1<0) {
							continue;
						}
						x--;
						break;
					case "S":
						System.out.println(" vers le sud");
						if(y-1<0) {
							continue;
						}
						y--;
						break;
					default:
						break;
					}
					break;
				case "D":
					System.out.print("Rotation droite");
					switch (orientation) {
					case "N":
						System.out.println(" vers est");
						orientation = "E";
						break;
					case "E":
						System.out.println(" vers sud");
						orientation = "S";
						break;
					case "W":
						System.out.println(" vers nord");
						orientation = "N";
						break;
					case "S":
						System.out.println(" vers ouest");
						orientation = "W";
						break;
					default:
						break;
					}
					break;
				case "G":
					System.out.print("Rotation gauche");
					switch (orientation) {
					case "N":
						System.out.println(" vers ouest");
						orientation = "W";
						break;
					case "E":
						System.out.println(" vers nord");
						orientation = "N";
						break;
					case "W":
						System.out.println(" vers sud");
						orientation = "S";
						break;
					case "S":
						System.out.println(" vers est");
						orientation = "E";
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		mowers.get(0).getPosition().setLocation(x,y);
		result += x + " "+ y + " " + orientation;
		}
		System.out.println(result);
		return result;
	}

	public ArrayList<Mower> getMowers() {
		return this.mowers;
	}
}
