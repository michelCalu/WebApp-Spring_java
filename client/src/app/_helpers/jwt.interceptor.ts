import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      console.log("INTERCEPTING REQUEST");
        // add authorization header with jwt token if available
        const currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
        if (currentUser && currentUser.token) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `Bearer ${currentUser.token}`,
                    'Access-Control-Allow-Origin': '*'
                }
            });
        }

        return next.handle(request);
    }
}