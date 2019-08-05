package com.tibbo.datatable;


//добавить 3 перемен из тестов
//

import com.tibbo.aggregate.common.Log;
import com.tibbo.aggregate.common.agent.Agent;
import com.tibbo.aggregate.common.agent.AgentContext;
import com.tibbo.aggregate.common.agent.HistoricalValue;
import com.tibbo.aggregate.common.context.CallerController;
import com.tibbo.aggregate.common.context.Context;
import com.tibbo.aggregate.common.context.ContextException;
import com.tibbo.aggregate.common.context.DefaultContextEventListener;
import com.tibbo.aggregate.common.context.EventDefinition;
import com.tibbo.aggregate.common.context.FunctionDefinition;
import com.tibbo.aggregate.common.context.FunctionImplementation;
import com.tibbo.aggregate.common.context.RequestController;
import com.tibbo.aggregate.common.context.VariableDefinition;
import com.tibbo.aggregate.common.context.VariableGetter;
import com.tibbo.aggregate.common.context.VariableSetter;
import com.tibbo.aggregate.common.data.Event;
import com.tibbo.aggregate.common.datatable.DataRecord;
import com.tibbo.aggregate.common.datatable.DataTable;
import com.tibbo.aggregate.common.datatable.SimpleDataTable;
import com.tibbo.aggregate.common.datatable.TableFormat;
import com.tibbo.aggregate.common.device.DisconnectionException;
import com.tibbo.aggregate.common.event.EventHandlingException;
import com.tibbo.aggregate.common.protocol.RemoteServer;
import com.tibbo.aggregate.common.util.SyntaxErrorException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AgrAgent {
    private static final String V_SETTING = "setting";
    private static final String V_PERIOD = "period";
    private static final String F_OPERATION = "operation";
    private static final String E_EVENT = "event";
    private static final String VF_SETTING_STRING = "string";
    private static final String VF_SETTING_INTEGER = "integer";
    private static final String VF_PERIOD_PERIOD = "period";
    private static final String FIF_OPERATION_LIMIT = "limit";
    private static final String FOF_OPERATION_RESULT = "result";
    private static final String EF_EVENT_DATA = "data";
    public static final TableFormat VFT_SETTING = new TableFormat(1,100);
    private static final TableFormat VFT_PERIOD;
    public static final TableFormat FIFT_OPERATION;
    private static final TableFormat FOFT_OPERATION;
    private static final TableFormat EFT_EVENT;
    private static DataTable setting;
    private static long period;

    public AgrAgent() {
    }

    public static void main(String[] args) {
        Log.start();
        beginDemo(period);
    }

    private static void beginDemo(final Long eventPeriod) {
        Thread eventGenerator = null;

        while(!Thread.currentThread().isInterrupted()) {
            try {
                final Agent agent = setUpDemoAgent(eventPeriod);
                agent.connect();
                eventGenerator = new Thread() {
                    public void run() {
                        while(!this.isInterrupted()) {
                            AgrAgent.setNewVariable(agent, eventPeriod);
                        }

                    }
                };
                runAgent(eventGenerator, agent);
            } catch (DisconnectionException var3) {
                var3.printStackTrace();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            pause();
            interruptThread(eventGenerator);
        }

    }

    public static Agent setUpDemoAgent(Long eventPeriod) {
        RemoteServer rls = new RemoteServer("104.248.243.143", 6480, "admin", "admin123");
        AgentContext agentContext = new AgrAgent.DemoAgentContext(rls, "ilyakrovat", true);
        Agent agent = new Agent(agentContext, false, false, 0);
        initializeAgentContext(agent.getContext(), eventPeriod);
        return agent;
    }

    public static void setNewVariable(Agent agent, Long period) {
        try {
            Thread.currentThread();
            Thread.sleep(period);
        } catch (InterruptedException var5) {
            return;
        }

        if (agent.getContext().isSynchronized()) {
            DataTable eventData = new SimpleDataTable(agent.getContext().getEventDefinition("event").getFormat(), new Object[]{new Float(Math.random() * 1000000.0D)});
            agent.getContext().fireEvent("event", 2, eventData);
            if (Math.random() > 0.1D) {
                try {
                    agent.getContext().setVariable("setting", new Object[]{agent.getContext().getVariable("setting").rec().getString("string"), Math.random() * 1000.0D});
                } catch (ContextException var4) {
                    var4.printStackTrace();
                }
            }
        }
    }

    private static void runAgent(Thread eventGenerator, Agent agent) throws SyntaxErrorException, DisconnectionException, IOException {
        eventGenerator.setDaemon(true);
        eventGenerator.start();

        while(!Thread.currentThread().isInterrupted()) {
            agent.run();
        }

        agent.disconnect();
    }

    private static void pause() {
        try {
            Thread.currentThread();
            Thread.sleep(1000L);
        } catch (InterruptedException var1) {
            System.exit(0);
        }

    }

    private static void interruptThread(Thread eventGenerator) {
        if (eventGenerator != null) {
            eventGenerator.interrupt();
        }

    }

    private static void initializeAgentContext(AgentContext context, final Long eventPeriod) {
        VariableDefinition vd = new VariableDefinition("setting", VFT_SETTING, true, true, "Tabular Setting", "remote");
        vd.setGetter(new VariableGetter() {
            public DataTable get(Context con, VariableDefinition def, CallerController caller, RequestController request) throws ContextException {
                return AgrAgent.setting;
            }
        });
        vd.setSetter(new VariableSetter() {
            public boolean set(Context con, VariableDefinition def, CallerController caller, RequestController request, DataTable value) throws ContextException {
                AgrAgent.setting = value;
                return true;
            }
        });
        context.addVariableDefinition(vd);
        vd = new VariableDefinition("period", VFT_PERIOD, true, true, "Event Generation Period", "remote");
        vd.setGetter(new VariableGetter() {
            public DataTable get(Context con, VariableDefinition def, CallerController caller, RequestController request) throws ContextException {
                return (new DataRecord(AgrAgent.VFT_PERIOD)).addLong(eventPeriod).wrap();
            }
        });
        vd.setSetter(new VariableSetter() {
            public boolean set(Context con, VariableDefinition def, CallerController caller, RequestController request, DataTable value) throws ContextException {
                AgrAgent.period = value.rec().getLong("period");
                Log.DEVICE_AGENT.info("Server has changed event generation period to: " + AgrAgent.period);
                return true;
            }
        });
        context.addVariableDefinition(vd);
        //
        vd = new VariableDefinition("table", StaticDataTable.getSimpleTable().getFormat(), true, true, "Simple Table", "remote");
        vd.setGetter(new VariableGetter() {
            public DataTable get(Context con, VariableDefinition def, CallerController caller, RequestController request) throws ContextException {
                return (new DataRecord(AgrAgent.VFT_PERIOD)).addLong(eventPeriod).wrap();
            }
        });
        vd.setSetter(new VariableSetter() {
            public boolean set(Context con, VariableDefinition def, CallerController caller, RequestController request, DataTable value) throws ContextException {
                AgrAgent.period = value.rec().getLong("table");
                Log.DEVICE_AGENT.info("Server has changed event generation period to: " + AgrAgent.period);
                return true;
            }
        });
        context.addVariableDefinition(vd);
        //
        vd = new VariableDefinition("Inner table", StaticDataTable.getInnerTable().getFormat(), true, true, "Inner Table", "remote");
        vd.setGetter(new VariableGetter() {
            public DataTable get(Context con, VariableDefinition def, CallerController caller, RequestController request) throws ContextException {
                return (new DataRecord(AgrAgent.VFT_PERIOD)).addLong(eventPeriod).wrap();
            }
        });
        vd.setSetter(new VariableSetter() {
            public boolean set(Context con, VariableDefinition def, CallerController caller, RequestController request, DataTable value) throws ContextException {
                AgrAgent.period = value.rec().getLong("Inner table");
                Log.DEVICE_AGENT.info("Server has changed event generation period to: " + AgrAgent.period);
                return true;
            }
        });
        context.addVariableDefinition(vd);
        //
        vd = new VariableDefinition("Big table", StaticDataTable.getBigTable().getFormat(), true, true, "Big Table", "remote");
        vd.setGetter(new VariableGetter() {
            public DataTable get(Context con, VariableDefinition def, CallerController caller, RequestController request) throws ContextException {
                return (new DataRecord(AgrAgent.VFT_PERIOD)).addLong(eventPeriod).wrap();
            }
        });
        vd.setSetter(new VariableSetter() {
            public boolean set(Context con, VariableDefinition def, CallerController caller, RequestController request, DataTable value) throws ContextException {
                AgrAgent.period = value.rec().getLong("Big table");
                Log.DEVICE_AGENT.info("Server has changed event generation period to: " + AgrAgent.period);
                return true;
            }
        });
        context.addVariableDefinition(vd);
        //
        FunctionDefinition fd = new FunctionDefinition("operation", FIFT_OPERATION, FOFT_OPERATION, "Agent Operation", "remote");
        fd.setImplementation(new FunctionImplementation() {
            public DataTable execute(Context con, FunctionDefinition def, CallerController caller, RequestController request, DataTable parameters) throws ContextException {
                int limit = parameters.rec().getInt("limit");
                int result = (int)(Math.random() * (double)limit);
                Log.DEVICE_AGENT.info("Server has executed random number generation operation with limit: " + limit + ", result: " + result);
                return (new DataRecord(def.getOutputFormat())).addInt(result).wrap();
            }
        });
        context.addFunctionDefinition(fd);
        EventDefinition ed = new EventDefinition("event", EFT_EVENT, "Agent Event", "remote");
        context.addEventDefinition(ed);
        context.addEventListener("eventConfirmed", new DefaultContextEventListener() {
            public void handle(Event event) throws EventHandlingException {
                Log.DEVICE_AGENT.info("Server has confirmed event with ID: " + event.getData().rec().getLong("id"));
            }
        });
    }

    static {
        VFT_SETTING.addField("<string><S><D=String Field>");
        VFT_SETTING.addField("<integer><I><D=Integer Field>");
        VFT_PERIOD = new TableFormat(1, 1, "<period><L><A=5000><D=Event Generation Period><V=<L=100 100000000>><E=period>");
        FIFT_OPERATION = new TableFormat(1, 1, "<limit><I><A=100><D=Limit>");
        FOFT_OPERATION = new TableFormat(1, 1, "<result><I><D=Result>");
        EFT_EVENT = new TableFormat(1, 1, "<data><F><D=Data>");
        setting = new SimpleDataTable(VFT_SETTING, true);
        period = 5000L;
    }

    private static class DemoAgentContext extends AgentContext {
        private boolean sentHistory = false;

        public DemoAgentContext(RemoteServer server, String name, boolean eventConfirmation) {
            super(server, name, eventConfirmation);
        }

        protected List<HistoricalValue> getHistory() {
            if (this.sentHistory) {
                return Collections.emptyList();
            } else {
                this.sentHistory = true;
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(13, -1);
                DataTable historicalValue = new SimpleDataTable(AgrAgent.VFT_SETTING, new Object[]{"Historical Value", 456});
                HistoricalValue hv = new HistoricalValue("setting", cal.getTime(), historicalValue);
                return Collections.singletonList(hv);
            }
        }
    }
}
