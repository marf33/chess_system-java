package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
	}
	
	//variavel auxiliar pode mover
	
	private boolean canMove(Position position) {
		//Saber se o cavalo pode mover para a posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	//Moviemntos possiveis de um Cavalo
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColums()];
		
		Position p = new Position(0, 0);
		
		p.setValues(position.getRow() - 1, position.getColumn() -2);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			}		
		
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}	
		
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow() +1, position.getColumn() - 2);
		//O cavalo pode mover para a posição p
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
	
}
