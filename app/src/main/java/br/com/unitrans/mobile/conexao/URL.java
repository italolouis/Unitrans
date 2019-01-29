package br.com.unitrans.mobile.conexao;

/**
 * Created by heloise on 21/10/16.
 */

public class URL {
    //URL de login
    public static final String LoginController = "/unitrans/controllers/LoginController.php";
    //Verifica Status Estudante
    public static final String VerificaEstudante = "/unitrans/controllers/EstudanteController.php?metodo=find&id=";
    //URL de retorno dos pontos
    public static final String RetornaPonto = "/unitrans/controllers/PontoController.php?metodo=list";
    //URL de upload de comprovante
    //public static final String FILE_UPLOAD_URL = "http://192.168.0.3/AndroidFileUpload/fileUpload.php";
    public static final String FILE_UPLOAD_URL = "http://192.168.0.3/unitrans/controllers/ComprovanteController.php";
    //URL de altera ponto
    public static final String AlteraPonto = "/unitrans/controllers/EstudanteController.php?metodo=alterarEmbarque";
    //Diretorio de inserir imagens
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

}
