import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomHttpInterceptor } from './http-interceptor.service';
@NgModule({
 providers: [
    CustomHttpInterceptor,
  {
    provide: HTTP_INTERCEPTORS,
    useClass: CustomHttpInterceptor,
    multi: true,
  },
 ],
})
export class InterceptorModule {}