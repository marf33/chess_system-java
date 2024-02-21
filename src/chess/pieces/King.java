package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}
	
	//variavel auxiliar pode mover
	
	private boolean canMove(Position position) {
		//Saber se o rei pode mover para a posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	//Moviemntos possiveis de um rei
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColums()];
		
		Position p = new Position(0, 0);
		
		//Posição acima do rei
		p.setValues(position.getRow() - 1, position.getColumn());
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição abaixo do rei
		p.setValues(position.getRow() + 1, position.getColumn());
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//Posição à esquerda do rei
		p.setValues(position.getRow(), position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			}		
		
		//Posição à direita do rei
		p.setValues(position.getRow(), position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		//Posição diagonal esquerda cima rei
		p.setValues(position.getRow() -1, position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal direita cima rei
		p.setValues(position.getRow() -1, position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal esquerda baixo rei
		p.setValues(position.getRow() +1, position.getColumn() - 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Posição diagonal direita baixo rei
		p.setValues(position.getRow() +1, position.getColumn() + 1);
		//O rei pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
	
	

}
