package tibbo.grep;

public class GrepTestHelper
{
  public static final String STRING_VALUE = "at org.h2.message.DbException.getJdbcSQLException(DbException.java:357)\n"
      + "\tat org.h2.message.DbException.get(DbException.java:179)\n"
      + "\tat org.h2.message.DbException.get(DbException.java:155)\n"
      + "\tat org.h2.expression.ExpressionColumn.optimize(ExpressionColumn.java:150)\n"
      + "\tat org.h2.command.dml.Select.prepare(Select.java:858)\n"
      + "\tat org.h2.command.Parser.prepareCommand(Parser.java:283)\n"
      + "\tat org.h2.engine.Session.prepareLocal(Session.java:611)\n"
      + "\tat org.h2.engine.Session.prepareCommand(Session.java:549)\n"
      + "\tat org.h2.jdbc.JdbcConnection.prepareCommand(JdbcConnection.java:1247)\n"
      + "\tat org.h2.jdbc.JdbcPreparedStatement.<init>(JdbcPreparedStatement.java:76)\n"
      + "\tat org.h2.jdbc.JdbcConnection.prepareStatement(JdbcConnection.java:694)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.prepare0(IgniteH2Indexing.java:539)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.prepareStatement(IgniteH2Indexing.java:509)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.prepareStatement(IgniteH2Indexing.java:476)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.prepareStatementAndCaches(IgniteH2Indexing.java:2629)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.prepareStatementAndCaches(IgniteH2Indexing.java:2647)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.parseAndSplit(IgniteH2Indexing.java:2350)\n"
      + "\tat org.apache.ignite.internal.processors.query.h2.IgniteH2Indexing.querySqlFields(IgniteH2Indexing.java:2190)\n"
      + "\tat org.apache.ignite.internal.processors.query.GridQueryProcessor$4.applyx(GridQueryProcessor.java:2135)\n"
      + "\tat org.apache.ignite.internal.processors.query.GridQueryProcessor$4.applyx(GridQueryProcessor.java:2130)\n"
      + "\tat org.apache.ignite.internal.util.lang.IgniteOutClosureX.apply(IgniteOutClosureX.java:36)\n"
      + "\tat org.apache.ignite.internal.processors.query.GridQueryProcessor.executeQuery(GridQueryProcessor.java:2707)\n"
      + "\tat org.apache.ignite.internal.processors.query.GridQueryProcessor.querySqlFields(GridQueryProcessor.java:2144)\n"
      + "\tat org.apache.ignite.internal.processors.odbc.jdbc.JdbcRequestHandler.executeQuery(JdbcRequestHandler.java:511)\n"
      + "\tat org.apache.ignite.internal.processors.odbc.jdbc.JdbcRequestHandler.doHandle(JdbcRequestHandler.java:245)\n"
      + "\tat org.apache.ignite.internal.processors.odbc.jdbc.JdbcRequestHandler.handle(JdbcRequestHandler.java:208)\n"
      + "\tat org.apache.ignite.internal.processors.odbc.ClientListenerNioListener.onMessage(ClientListenerNioListener.java:162)\n"
      + "\tat org.apache.ignite.internal.processors.odbc.ClientListenerNioListener.onMessage(ClientListenerNioListener.java:45)\n"
      + "\tat org.apache.ignite.internal.util.nio.GridNioFilterChain$TailFilter.onMessageReceived(GridNioFilterChain.java:279)\n"
      + "\tat org.apache.ignite.internal.util.nio.GridNioFilterAdapter.proceedMessageReceived(GridNioFilterAdapter.java:109)\n"
      + "\tat org.apache.ignite.internal.util.nio.GridNioAsyncNotifyFilter$3.body(GridNioAsyncNotifyFilter.java:97)\n"
      + "\tat org.apache.ignite.internal.util.worker.GridWorker.run(GridWorker.java:120)\n"
      + "\tat org.apache.ignite.internal.util.worker.GridWorkerPool$1.run(GridWorkerPool.java:70)\n"
      + "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n"
      + "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n"
      + "\tat java.lang.Thread.run(Thread.java:748)\n"
      + "\t27.05.2019 15:30:56,237 WARN  com.datastax.driver.core.NettyUtil Found Netty's native epoll transport, but not running on linux-based operating system. Using NIO instead.     -     -     -     -     -     -     -     -     -     -     [Master] com.datastax.driver.core.NettyUtil.<clinit> (NettyUtil.java:63)\n"
      + "\t27.05.2019 15:31:25,129 ERROR com.datastax.driver.core.ControlConnection [Control connection] Cannot connect to any host, scheduling retry in 1000 milliseconds     -     -     -     -     -     -     -     -     -     -     [aggregate_cluster-reconnection-0] com.datastax.driver.core.ControlConnection$1.onConnectionException (ControlConnection.java:153)\n"
      + "\t27.05.2019 15:31:26,133 ERROR com.datastax.driver.core.ControlConnection [Control connection] Cannot connect to any host, scheduling retry in 2000 milliseconds     -     -     -     -     -     -     -     -     -     -     [aggregate_cluster-reconnection-0] com.datastax.driver.core.ControlConnection$1.onConnectionException (ControlConnection.java:153)\n"
      + "\t27.05.2019 15:34:59,061 WARN  com.datastax.driver.core.NettyUtil Found Netty's native epoll transport, but not running on linux-based operating system. Using NIO instead.     -     -     -     -     -     -     -     -     -     -     [Master] com.datastax.driver.core.NettyUtil.<clinit> (NettyUtil.java:63)\n"
      + "\t27.05.2019 15:35:40,042 ERROR com.datastax.driver.core.ControlConnection [Control connection] Cannot connect to any host, scheduling retry in 1000 milliseconds     -     -     -     -     -     -     -     -     -     -     [aggregate_cluster-reconnection-0] com.datastax.driver.core.ControlConnection$1.onConnectionException (ControlConnection.java:153)\n"
      + "\t27.05.2019 15:40:56,185 WARN  com.datastax.driver.core.NettyUtil Found Netty's native epoll transport, but not running on linux-based operating system. Using NIO instead.     -     -     -     -     -     -     -     -     -     -     [Master] com.datastax.driver.core.NettyUtil.<clinit> (NettyUtil.java:63)\n"
      + "\t27.05.2019 15:42:44,577 ERROR com.datastax.driver.core.ControlConnection [Control connection] Cannot connect to any host, scheduling retry in 1000 milliseconds     -     -     -     -     -     -     -     -     -     -     [aggregate_cluster-reconnection-0] com.datastax.driver.core.ControlConnection$1.onConnectionException (ControlConnection.java:153)\n"
      + "\t27.05.2019 15:48:01,230 WARN  com.datastax.driver.core.NettyUtil Found Netty's native epoll transport, but not running on linux-based operating system. Using NIO instead.     -     -     -     -     -     -     -     -     -     -     [Master] com.datastax.driver.core.NettyUtil.<clinit> (NettyUtil.java:63)\n"
      + "\t27.05.2019 15:51:42,254 ERROR com.datastax.driver.core.ControlConnection [Control connection] Cannot connect to any host, scheduling retry in 1000 milliseconds     -     -     -     -     -     -     -     -     -     -     [aggregate_cluster-reconnection-0] com.datastax.driver.core.ControlConnection$1.onConnectionException (ControlConnection.java:153)\n"
      + "\t27.05.2019 15:53:47,518 WARN  com.datastax.driver.core.NettyUtil Found Netty's native epoll transport, but not running on linux-based operating system. Using NIO instead.     -     -     -     -     -     -     -     -     -     -     [Master] com.datastax.driver.core.NettyUtil.<clinit> (NettyUtil.java:63)\n"
      + "\t27.05.2019 15:58:01,630 ERROR com.datastax.driver.core.RequestHandler Unexpected error while querying /172.20.30.200     -     -     -   ";
}
