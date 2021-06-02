package nextstep.subway.section.domain;

import nextstep.subway.common.BaseEntity;
import nextstep.subway.line.domain.Line;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Section extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "up_station_id")
  private Station upStation;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "down_station_id")
  private Station downStation;

  @Column(nullable = false)
  @Embedded
  private Distance distance;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "line_id")
  private Line line;

  protected Section() {}

  public Section(Station upStation, Station downStation, int distance) {
    this.upStation = upStation;
    this.downStation = downStation;
    this.distance = Distance.from(distance);
  }

  public Long getId() {
    return id;
  }

  public Station getUpStation() {
    return upStation;
  }

  public Station getDownStation() {
    return downStation;
  }

  public Integer getDistance() {
    return distance.getNumber();
  }

  public Line getLine() {
    return line;
  }

  public void addLine(Line line) {
    this.line = line;
  }

  public List<Station> getUpAndDownStations() {
    return Stream.of(upStation, downStation)
            .collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Section section = (Section) o;
    return id.equals(section.id) && upStation.equals(section.upStation) && downStation.equals(section.downStation) && distance.equals(section.distance) && line.equals(section.line);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, upStation, downStation, distance, line);
  }
}