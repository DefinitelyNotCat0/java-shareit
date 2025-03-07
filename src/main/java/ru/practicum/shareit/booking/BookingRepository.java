package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findAllByBookerIdOrderByStartDesc(Long bookerId);

    @Query("select b from BookingEntity b " +
            "inner join b.booker u " +
            "where u.id = ?1 " +
            "and ?2 between b.start and b.end " +
            "order by b.start desc")
    List<BookingEntity> findAllByBookerIdAndCurrentOrderByStartDesc(Long bookerId, LocalDateTime currentDate);

    @Query("select b from BookingEntity b " +
            "inner join b.booker u " +
            "where u.id = ?1 " +
            "and ?2 > b.end " +
            "order by b.start desc")
    List<BookingEntity> findAllByBookerIdAndPastOrderByStartDesc(Long bookerId, LocalDateTime currentDate);

    @Query("select b from BookingEntity b " +
            "inner join b.booker u " +
            "where u.id = ?1 " +
            "and ?2 < b.start " +
            "order by b.start desc")
    List<BookingEntity> findAllByBookerIdAndFutureOrderByStartDesc(Long bookerId, LocalDateTime currentDate);

    List<BookingEntity> findAllByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    @Query("select b from BookingEntity b " +
            "inner join b.item t " +
            "inner join t.owner u " +
            "where u.id = ?1 " +
            "order by b.start desc")
    List<BookingEntity> findAllByOwnerIdOrderByStartDesc(Long ownerId);

    @Query("select b from BookingEntity b " +
            "inner join b.item t " +
            "inner join t.owner u " +
            "where u.id = ?1 " +
            "and ?2 between b.start and b.end " +
            "order by b.start desc")
    List<BookingEntity> findAllByOwnerIdAndCurrentOrderByStartDesc(Long ownerId, LocalDateTime currentDate);

    @Query("select b from BookingEntity b " +
            "inner join b.item t " +
            "inner join t.owner u " +
            "where u.id = ?1 " +
            "and ?2 > b.end " +
            "order by b.start desc")
    List<BookingEntity> findAllByOwnerIdAndPastOrderByStartDesc(Long ownerId, LocalDateTime currentDate);

    @Query("select b from BookingEntity b " +
            "inner join b.item t " +
            "inner join t.owner u " +
            "where u.id = ?1 " +
            "and ?2 < b.start " +
            "order by b.start desc")
    List<BookingEntity> findAllByOwnerIdAndFutureOrderByStartDesc(Long ownerId, LocalDateTime currentDate);

    @Query("select b from BookingEntity b " +
            "inner join b.item t " +
            "inner join t.owner u " +
            "where u.id = ?1 " +
            "and b.status = ?2" +
            "order by b.start desc")
    List<BookingEntity> findAllByOwnerIdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status);
}
