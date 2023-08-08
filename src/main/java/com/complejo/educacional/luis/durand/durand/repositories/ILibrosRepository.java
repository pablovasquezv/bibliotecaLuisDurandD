package com.complejo.educacional.luis.durand.durand.repositories;

import com.complejo.educacional.luis.durand.durand.models.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Pablo
 */
public interface ILibrosRepository extends JpaRepository<Libro, Long> {
    /**
     * En este ejemplo, estamos utilizando Spring Data JPA para definir un repositorio de libros (LibroRepository).
     * La interfaz extiende JpaRepository y proporciona métodos predefinidos para realizar operaciones CRUD en la
     * entidad Libro.
     * <p>
     * Luego, utilizamos la anotación @Query para especificar una consulta JPQL personalizada. La consulta selecciona
     * todos los libros (select l from Libro l), y utiliza la cláusula left join fetch para realizar una carga eager
     * de las entidades relacionadas autor, categoria y editorial.
     * <p>
     * La consulta toma un argumento adicional Sort sort, que puedes usar para ordenar los resultados.
     *
     * @param sort
     * @return
     */
    @Query(value = "select l from Libro l left join fetch l.autor e left join l.categoria e left join l.editorial")
    List<Libro> findAllSortLibros(Sort sort);

    /***
     * En esta consulta, hemos utilizado Page en lugar de List para obtener resultados paginados.
     * La anotación @Query tiene dos parámetros: value para la consulta principal y countQuery para la consulta de
     * conteo de registros.
     *
     * En la consulta principal, estamos seleccionando los libros y utilizando las cláusulas LEFT JOIN FETCH para
     * realizar una carga eager de las entidades relacionadas autor, categoria y editorial.
     *
     * En la consulta de conteo de registros, estamos utilizando la función COUNT() para contar el número total de
     * registros en la tabla Libro.
     *
     * La interfaz LibroRepository extiende JpaRepository e incluye el método findAllPageLibros() que toma un
     * objeto Pageable como parámetro y devuelve una instancia de Page<Libro>. Esto te permitirá obtener resultados
     * paginados y acceder a información adicional sobre la paginación, como el número total de páginas, el número t
     * otal de elementos, etc.
     * @param pageable
     * @return
     */
    @Query(value = "select l from Libro l left join fetch l.autor e left join l.categoria e left join l.editorial",
            countQuery = "select (l) from Libro l left join fetch l.autor e left join l.categoria e left join l.editorial")
    public Page<Libro> findAllPageLibros(Pageable pageable);

    /**
     * En esta consulta, la anotación @Query para especificar una consulta personalizada en JPQL para buscar un libro por
     * su ID. Aquí está la explicación de cada componente de la consulta:
     * <p>
     * select l from Libro l: esta parte de la consulta especifica que queremos seleccionar la entidad Libro y asignarle
     * el alias l para su uso en la consulta.
     * <p>
     * left join fetch l.autor e: esta cláusula left join fetch se utiliza para realizar un join con la entidad autor
     * relacionada a través de la propiedad autor en la entidad Libro. El alias e se asigna a esta entidad autor para
     * su uso en la consulta.
     * <p>
     * left join l.categoria e: esta cláusula left join se utiliza para realizar un join con la entidad categoria
     * relacionada a través de la propiedad categoria en la entidad Libro. El alias e se asigna a esta entidad categoria
     * para su uso en la consulta.
     * <p>
     * left join l.editorial: esta cláusula left join se utiliza para realizar un join con la entidad editorial
     * relacionada a través de la propiedad editorial en la entidad Libro. No se especifica un alias para esta entidad
     * en la consulta.
     * <p>
     * where l.id=:id: esta cláusula where se utiliza para filtrar los resultados de la consulta. En este caso, estamos
     * filtrando los libros por su ID, utilizando el parámetro :id para representar el valor del ID que se busca.
     * <p>
     * public Libro findByIdLibro(long id): este es el método de repositorio que define la consulta. Toma un parámetro
     * long id y devuelve un objeto de tipo Libro.
     *
     * @param id
     * @return
     */
    @Query(value = "select l from Libro l left join fetch l.autor e left join l.categoria e left join l.editorial where l.id=:id")
    public Libro findByIdLibro(long id);


}
