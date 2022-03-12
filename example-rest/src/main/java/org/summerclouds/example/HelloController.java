package org.summerclouds.example;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.summerclouds.common.core.error.ErrorRuntimeException;
import org.summerclouds.common.core.log.MLog;
import org.summerclouds.common.core.tool.MThread;
import org.summerclouds.common.core.tool.MTracing;
import org.summerclouds.common.core.tracing.IScope;

@RestController
public class HelloController extends MLog {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/hello")
	@Secured("ace_rest")
	public Hello greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		log().i("call /hello");
		try (IScope scope = MTracing.enter("aloa", "a", "b") ) {
			log().i("in the scope");
			MThread.sleepForSure(1000);
		}
		
//		Tracer tracer = MSpring.lookup(Tracer.class);
//		System.out.println("CURRENT " + tracer.currentSpan());
//		tracer.currentSpan().tag("a", "b");
//		Span span = tracer.currentSpan().annotate("boarrr");
//		span.finish();
		
//		Tracer tracer = MSpring.lookup(Tracer.class);
//		Span current = tracer.currentSpan();
//		System.out.println("CURRENT: " + current);
//		current.tag("a", "b");
//		Span newSpan = tracer.nextSpan().name("calculateTax");
//		try (Tracer.SpanInScope ws = tracer.withSpan(newSpan.start())) {
//			System.out.println("CURRENT2: " + tracer.currentSpan());
//		}
//		newSpan.end();
//		System.out.println("CURRENT3: " + tracer.currentSpan());
		
		return new Hello(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/error400")
	public Hello error(@RequestParam(value = "name", defaultValue = "World") String name) {
		log().i("call /error400");
		throw new ErrorRuntimeException("Test", "arg1");
	}

}
