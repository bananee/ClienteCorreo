
public class MarcarLeido extends Filtro {
	
	public MarcarLeido(Condicion condi, Envoltura env){
		this.cond = condi;
		this.envol = env;
	}
	@Override
	public void filtrar() {
		this.envol.leido = true;
		
	}

}
