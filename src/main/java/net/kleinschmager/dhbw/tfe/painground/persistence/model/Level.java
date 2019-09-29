package net.kleinschmager.dhbw.tfe.painground.persistence.model;

public enum Level {

    /* novice = green, advanced = violet, expert = red */
	NOVICE("#1da801"), ADVANCED("#5959ff"), EXPERT("#ff6464");

	private String htmlColor;

	private Level(String htmlColor) {
		this.htmlColor = htmlColor;
	}

	public String getHtmlColor() {
		return this.htmlColor;
	}
}
