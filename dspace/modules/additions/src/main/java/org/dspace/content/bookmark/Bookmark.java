package org.dspace.content.bookmark;

import org.dspace.content.Item;
import org.dspace.eperson.EPerson;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bookmark")
public class Bookmark {

    @Id
    @Column(name = "bookmark_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="bookmark_seq")
    @SequenceGenerator(name="bookmark_seq", sequenceName="bookmark_seq", allocationSize = 1, initialValue = 1)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator")
    private EPerson creator;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item")
    private Item item;

    protected Bookmark(){

    }
    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public EPerson getCreator() {
        return creator;
    }

    public void setCreator(EPerson creator) {
        this.creator = creator;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
