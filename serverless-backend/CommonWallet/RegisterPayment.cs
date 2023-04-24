using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using CommonWallet.Models;

namespace CommonWallet
{
    public static class RegisterPayment
    {
        [FunctionName("RegisterPayment")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "post", Route = "newpayment")]
            HttpRequest req,
            [Sql(commandText:"dbo.Payment", connectionStringSetting:"SqlConnectionString")] IAsyncCollector<NewPayment> payments,
            ILogger log)
        {
            log.LogInformation("RegisterPayment function processed a request");
            var jsonString = await new StreamReader(req.Body).ReadToEndAsync();
            NewPayment payment = JsonConvert.DeserializeObject<NewPayment>(jsonString);
            log.LogInformation($"New payment: {payment}");
            await payments.AddAsync(payment);
            await payments.FlushAsync();
            return new OkResult();
        }
    }
}
