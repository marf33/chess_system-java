package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	//Retorna uma posição no formato do xadrez
	public ChessPosition getChessPosition() {
		//Posição da minha peça
		return ChessPosition.fromPosition(position);
	}
	
	//Para saber se existe uma peça adversária em uma dada posição(casa)
	protected boolean isThereOpponentPiece(Position position) {
		//p recebe a posição da peça que estiver no tabuleiro
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		//verificar se a peça que esta nesta posição é a peça adversaría ou seja diferente da do jogador que está a jogar
		return p != null && p.getColor() != color;
	}
	
}
