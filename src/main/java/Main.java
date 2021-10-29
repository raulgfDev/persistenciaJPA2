import entity.Cliente;
import javax.persistence.*;

//lib' I need 1.Hibernate, after 2. mysql connection, look version
//mapeo TA' desde view window and 'persistance/default' click right
//add to file persistence.xml three lines of properties
public class Main {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

//            Cliente cliente = new Cliente();
//           RRR=config DB autoincrement//cliente.setId(1);
//            cliente.setNombre("Virginia");
//            cliente.setApellido("Segura");
//            cliente.setTelefono("62345879");

//            entityManager.persist(cliente);

            //config consulta HQL en la cl de origen
            TypedQuery<Cliente> listResultadoHQL = entityManager.createNamedQuery("Cliente.byApellido",Cliente.class);
            listResultadoHQL.setParameter(1,"Segura");
            //for exe HQL on console go to view window 'persistance/default/click right and consoleJPA
            for (Cliente c :
                    listResultadoHQL.getResultList()) {
                System.out.println(c);
            }

            //necesito config sql dialects en configOptions
            Query miNativa = entityManager.createNativeQuery("select telefono tlf, nombre name from cliente where nombre = :nombre");
            //IMP min17'vd exe en consolaSQL configParam = ctrl + intro cursor in statementSQL
            miNativa.setParameter("nombre","Raul");
            System.out.println(miNativa.getResultList());

            transaction.commit();

            System.out.println("Exitooooo");

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

}
