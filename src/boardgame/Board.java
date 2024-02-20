package boardgame;

public class Board {

	private int rows;
	private int colums;
	private Piece[][] pieces;
	
	public Board(int rows, int colums) {
		this.rows = rows;
		this.colums = colums;
		pieces = new Piece[rows][colums];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColums() {
		return colums;
	}

	public void setColums(int colums) {
		this.colums = colums;
	}
	
	//Retornar a matriz nas posições row e column
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	//Retorna as posições das peças 
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//Colocar a peça nessa posição do tabuleiro
	public void placePiece(Piece piece, Position position) {
		//Matriz na posição dada atiribuida à peça informada
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
}
