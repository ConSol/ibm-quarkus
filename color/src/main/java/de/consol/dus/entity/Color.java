package de.consol.dus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_color")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Color {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdSequence")
  @SequenceGenerator(name = "UserIdSequence", sequenceName = "app_color_seq_id", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "hexdec")
  private String hexdec;

  @Column(name = "name")
  private String name;
}
