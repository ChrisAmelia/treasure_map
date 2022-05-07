package com.treasuremap.app.model;

/**
 * TileType
 */
public enum TileType {
	PRAIRIE {
		public String toString() {
			return " ";
		}
	},

	MOUNTAIN {
		public String toString() {
			return "x";
		}
	}
}
