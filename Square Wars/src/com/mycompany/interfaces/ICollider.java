package com.mycompany.interfaces;

import com.mycompany.a3.GameObject;

public interface ICollider {
	public boolean collidesWith(GameObject otherObject);
	public void handleCollision(GameObject otherObject);
}
