//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gargoylesoftware.htmlunit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebConsole implements Serializable {
    private WebConsole.Formatter formatter_ = new WebConsole.DefaultFormatter();
    private WebConsole.Logger logger_ = new WebConsole.DefaultLogger();

    public WebConsole() {
    }

    public void setFormatter(WebConsole.Formatter formatter) {
        this.formatter_ = formatter;
    }

    public WebConsole.Formatter getFormatter() {
        return this.formatter_;
    }

    public void setLogger(WebConsole.Logger logger) {
        this.logger_ = logger;
    }

    public WebConsole.Logger getLogger() {
        return this.logger_;
    }

    public void trace(Object... args) {
        if(this.logger_.isTraceEnabled()) {
            this.logger_.trace(this.process(args));
        }

    }

    public void debug(Object... args) {
        if(this.logger_.isDebugEnabled()) {
            this.logger_.debug(this.process(args));
        }

    }

    public void info(Object... args) {
        if(this.logger_.isInfoEnabled()) {
            this.logger_.info(this.process(args));
        }

    }

    public void warn(Object... args) {
        if(this.logger_.isWarnEnabled()) {
            this.logger_.warn(this.process(args));
        }

    }

    public void error(Object... args) {
        if(this.logger_.isErrorEnabled()) {
            this.logger_.error(this.process(args));
        }

    }

    private String process(Object[] objs) {
        if(objs == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();
            LinkedList args = new LinkedList(Arrays.asList(objs));
            WebConsole.Formatter formatter = this.getFormatter();
            if(args.size() > 1 && args.get(0) instanceof String) {
                StringBuilder msg = new StringBuilder((String)args.remove(0));

                for(int o = msg.indexOf("%"); o > -1 && o < msg.length() - 1 && args.size() > 0; o = msg.indexOf("%", o)) {
                    if(o != 0 && msg.charAt(o - 1) == 37) {
                        msg.replace(o, o + 1, "");
                    } else {
                        char type = msg.charAt(o + 1);
                        String replacement = null;
                        switch(type) {
                            case 'd':
                            case 'i':
                                replacement = formatter.parameterAsInteger(pop(args));
                                break;
                            case 'f':
                                replacement = formatter.parameterAsFloat(pop(args));
                                break;
                            case 'o':
                            case 's':
                                replacement = formatter.parameterAsString(pop(args));
                        }

                        if(replacement != null) {
                            msg.replace(o, o + 2, replacement);
                            o += replacement.length();
                        } else {
                            ++o;
                        }
                    }
                }

                sb.append(msg);
            }

            Object var10;
            for(Iterator var9 = args.iterator(); var9.hasNext(); sb.append(formatter.printObject(var10))) {
                var10 = var9.next();
                if(sb.length() != 0) {
                    sb.append(' ');
                }
            }

            return sb.toString();
        }
    }

    private static Object pop(List<Object> list) {
        return list.isEmpty()?null:list.remove(0);
    }

    private static class DefaultLogger implements WebConsole.Logger, Serializable {
        private static final Log LOG = LogFactory.getLog(WebConsole.class);

        private DefaultLogger() {
        }

        public boolean isTraceEnabled() {
            return LOG.isTraceEnabled();
        }

        public void trace(Object message) {
            if(LOG.isTraceEnabled()) {
                LOG.trace(message);
            }

        }

        public boolean isDebugEnabled() {
            return LOG.isDebugEnabled();
        }

        public void debug(Object message) {
            if(LOG.isDebugEnabled()) {
                LOG.debug(message);
            }

        }

        public boolean isInfoEnabled() {
            return LOG.isInfoEnabled();
        }

        public void info(Object message) {
            LOG.info(message);
        }

        public boolean isWarnEnabled() {
            return LOG.isWarnEnabled();
        }

        public void warn(Object message) {
            LOG.warn(message);
        }

        public boolean isErrorEnabled() {
            return LOG.isErrorEnabled();
        }

        public void error(Object message) {
            LOG.info(message);
        }
    }

    private static class DefaultFormatter implements WebConsole.Formatter, Serializable {
        private DefaultFormatter() {
        }

        public String printObject(Object o) {
            return this.parameterAsString(o);
        }

        public String parameterAsString(Object o) {
            return o != null?o.toString():"null";
        }

        public String parameterAsInteger(Object o) {
            if(o instanceof Number) {
                return Integer.toString(((Number)o).intValue());
            } else {
                if(o instanceof String) {
                    try {
                        return Integer.toString(Integer.parseInt((String)o));
                    } catch (NumberFormatException var3) {
                        ;
                    }
                }

                return "NaN";
            }
        }

        public String parameterAsFloat(Object o) {
            if(o instanceof Number) {
                return Float.toString(((Number)o).floatValue());
            } else {
                if(o instanceof String) {
                    try {
                        return Float.toString(Float.parseFloat((String)o));
                    } catch (NumberFormatException var3) {
                        ;
                    }
                }

                return "NaN";
            }
        }
    }

    public interface Formatter {
        String printObject(Object var1);

        String parameterAsString(Object var1);

        String parameterAsInteger(Object var1);

        String parameterAsFloat(Object var1);
    }

    public interface Logger {
        boolean isTraceEnabled();

        void trace(Object var1);

        boolean isDebugEnabled();

        void debug(Object var1);

        boolean isInfoEnabled();

        void info(Object var1);

        boolean isWarnEnabled();

        void warn(Object var1);

        boolean isErrorEnabled();

        void error(Object var1);
    }
}
