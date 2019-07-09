package character;

public abstract class PlayerAbstractFactory {
	protected abstract Player makePlayer();
	public Player getPlayer() {
		return this.makePlayer();
	}
}
