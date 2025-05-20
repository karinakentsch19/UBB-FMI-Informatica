package ubb.ro.socialnetworkgui.Repository.Paging;

import ubb.ro.socialnetworkgui.Domain.Entity;
import ubb.ro.socialnetworkgui.Repository.AbstractRepository;

public interface PagingRepository<ID, E extends Entity<ID>> extends AbstractRepository<ID, E> {
    Page<E> findAllOnPage(Pageable pageable);
}
