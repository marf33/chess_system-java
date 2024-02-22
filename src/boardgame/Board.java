package boardgame;

public class Board {

	private int rows;
	private int colums;
	private Piece[][] pieces;
	
	public Board(int rows, int colums) {
		if (rows < 1 || colums < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		
		this.rows = rows;
		this.colums = colums;
		pieces = new Piece[rows][colums];
	}

	public int getRows() {
		return rows;
	}

	public int getColums() {
		return colums;
	}
	
	//Retornar a matriz nas posições row e column
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		
		return pieces[row][column];
	}
	
	//Retorna as posições das peças 
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//Colocar a peça nessa posição do tabuleiro
	public void placePiece(Piece piece, Position position) {
		if (thereIsApiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		//Matriz na posição dada atiribuida à peça informada
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	//remover peças
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		
		if (piece(position) == null) {
			return null;
		}
		
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	//Veriicar se a posição exsite por linha e coluna
	private boolean positionExists(int row, int column) {
		//Verficar se a posição existe
		return row >= 0 && row < rows && column >= 0 && column < colums;
	}
	
	//Verificar se a posição existe por posição
	public boolean positionExists(Position position) {
		//Verficar se a posição existe
		return positionExists(position.getRow(),position.getColumn());
	}
	
	//Verificar se tem uma peça nessa posição
	public boolean thereIsApiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		
		return piece(position) != null;
	}
	
}
