package benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

// --- //

case class Pair(col: Int, row: Int)

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
class SetBench {
  var smallList: List[Pair] = _
  var bigList: List[Pair] = _
  var hugeList: List[Pair] = _

  @Setup
  def setup(): Unit = {
    smallList = (List.range(1, 100) ++ List.range(1, 100)).map(n => Pair(n,n))
    bigList = (List.range(1, 1000) ++ List.range(1, 1000)).map(n => Pair(n,n))
    hugeList = (List.range(1, 10000) ++ List.range(1, 10000)).map(n => Pair(n,n))
  }

  @Benchmark
  def withDistinct: List[Pair] = smallList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct
  @Benchmark
  def withDistinct2: List[Pair] = bigList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct
  @Benchmark
  def withDistinct3: List[Pair] = hugeList.map { case Pair(c,r) => Pair(c + 1, r) }.distinct

  @Benchmark
  def withSet: List[Pair] = smallList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.toList
  @Benchmark
  def withSet2: List[Pair] = bigList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.toList
  @Benchmark
  def withSet3: List[Pair] = hugeList.map { case Pair(c,r) => Pair(c + 1, r) }.toSet.toList

}
