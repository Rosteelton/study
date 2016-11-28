package differentTasks.akkaExamples.akkaStream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._

object App {

  def some() {
    implicit val system = ActorSystem("reactive-tweets")
    implicit val materializer = ActorMaterializer()


    val source: Source[Int, NotUsed] = Source(1 to 100)
   // source.runForeach(i => println(i))



/*    val factorials = source.scan(BigInt(1))((acc, next) => acc * next)



    val result =
      factorials
        .map(num => ByteString(s"$num\n"))
        .runWith(FileIO.toFile(new File("factorials.txt")))

    factorials.runForeach(println(_))

    val duration = new FiniteDuration(1,java.util.concurrent.TimeUnit.SECONDS)

    val done: Future[Done] =
      factorials
        .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
        .throttle(1, duration, 1, ThrottleMode.shaping)
        .runForeach(println)

    val mass = (1 to 10).toList
    val tmp2 = mass.foldLeft(1)((a,b) => a*b)
    println(tmp2)*/

/*    val source = Source(1 to 10)
    val sink = Sink.fold[Int, Int](0)(_ + _)

    // connect the Source to the Sink, obtaining a RunnableGraph
    val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)

    // materialize the flow and get the value of the FoldSink
    val sum: Future[Int] = runnable.run()

    val sum2 = source.runWith(sink)*/

    final case class Author(handle: String)

    final case class Hashtag(name: String)

    final case class Tweet(author: Author, timestamp: Long, body: String) {
      def hashtags: Set[Hashtag] =
        body.split(" ").collect { case t if t.startsWith("#") => Hashtag(t) }.toSet
    }

    val akka = Hashtag("#akka")

    val tweets: Source[Tweet, NotUsed] = Source{
      List(new Tweet(new Author("asd"), 123, "aasdfasdf #akka"))
    }

    val authors: Source[Author, NotUsed] =
      tweets
        .filter(_.hashtags.contains(akka))
        .map(_.author)

    authors.runWith(Sink.foreach(x=>println(x.handle)))


    val writeAuthors: Sink[Author, Unit] = ???
    val writeHashtags: Sink[Hashtag, Unit] = ???

    val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._

      val bcast = b.add(Broadcast[Tweet](2))
      tweets ~> bcast.in
      bcast.out(0) ~> Flow[Tweet].map(_.author) ~> writeAuthors
      bcast.out(1) ~> Flow[Tweet].mapConcat(_.hashtags.toList) ~> writeHashtags
      ClosedShape
    })
    g.run()
  }



}
