package gov.abrs.etms.service.log;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

@Component
public class AuditLogQueue extends ConcurrentLinkedQueue<Map<String, Object>> {

	private static final long serialVersionUID = -4536427723472060276L;

}
